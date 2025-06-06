package org.example;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;
public class Database {
    private List<Seller> sellers;
    private List<Manager> managers;
    private List<Brand> brands;
    private Boss boss;
    private static List<Department> department;
    private List<Product> products;
    private List<PurchaseRecord> sales;
    private Connection conection;
    private static Database instance;

    public Database() {
        connection();  // połączenie wywołujemy raz podczas tworzenia obiektu
        // inicjalizacja list
        sellers = new ArrayList<>();
        managers = new ArrayList<>();
        brands = new ArrayList<>();
        department = new ArrayList<>();
        products = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public static Database getInstance() { //Singleton
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void connection() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "datax";
        try {
            Class.forName("org.postgresql.Driver");
            this.conection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection sequscees");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void setDepartmentsFromDB() throws SQLException {
        String sql = "SELECT * FROM Department";
        PreparedStatement st = conection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            department.add(new Department(rs.getString("name")));
        }
    }


    public void setManagersFromDB() throws SQLException {
        String sql = "SELECT * FROM Manager";
        PreparedStatement st = conection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String depname = rs.getString("department");
            float income = rs.getFloat("income");
            float commission = rs.getFloat("commission");
            int experience = rs.getInt("experience");
            managers.add(new Manager(id, name,getDep(depname), income, commission, experience));
        }
    }

    public Department getDep(String depName) {
        if (depName == null) return null;
        for (Department dep : department) {
            if (depName.trim().equalsIgnoreCase(dep.getName().trim())) {
                return dep;
            }
        }
        return null;
    }

    public void setSellersFromDB(int min,int max) throws SQLException {
        Random rand = new Random();
        String sql = "SELECT * FROM Seler";
        PreparedStatement st = conection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String depname = rs.getString("department");
            float income = rs.getFloat("salary");
            float commision = rs.getFloat("commission");
            int salesCount = rs.getInt("salescount");
            float rating = rs.getFloat("rating");
            int experience = min + rand.nextInt(max - min);
//            int experience = rs.getInt("experience_years");
            sellers.add(new Seller(id, name,getDep(depname),income, commision,experience, salesCount, rating));
//            for (Department dep : department) {
//                if (departmentname.equals(dep.getName())) {
//                    sellers.add(new Seller(id, name, dep, income, commision, salesCount, rating, experience));
//                }
//            }
        }
    }

    public void setBrandFromDB() throws SQLException {
        String sql = "SELECT * FROM Brand";
        PreparedStatement st = conection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            String depname = rs.getString("department");
            brands.add(new Brand(name, getDep(depname)));
            System.out.println("Brand: " + name);
        }
    }

    public void setProductsFromDB() throws SQLException {
        String sql = "SELECT * FROM Product";
        PreparedStatement st = conection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            float price = rs.getFloat("price");
            int quantity = rs.getInt("quantity");
            String description = rs.getString("description");
            String brand = rs.getString("brand");
            Brand brand1 = getBrand(brand);
            if (brand1 == null) {
                System.out.println("❌ Brand nie znaleziono: " + brand);
                continue; // albo throw new RuntimeException()
            }

            products.add(new Product(name, price, quantity, description, getBrand(brand)));
        }
    }

    public Brand getBrand(String brandName) {
        if (brandName == null) return null;
        for (Brand brand : brands) {
            if (brandName.trim().equalsIgnoreCase(brand.getName().trim())) {
                return brand;
            }
        }
        return null;
    }

    public void setBossFromDB() throws SQLException {
        String sql = "SELECT * FROM Boss";
        PreparedStatement st = conection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("unik_id");
            String name = rs.getString("name");
            float income = rs.getFloat("income");
            boss = new Boss();
        }
    }

    public void updateQuantityInDB(Product product, int quantity) throws SQLException {
        String sql = "UPDATE Product SET quantity = ? WHERE name = ?";
        PreparedStatement st = conection.prepareStatement(sql);
        st.setInt(1, quantity);
        st.setString(2, product.getName());
        st.executeUpdate();
    }

    public void updateDataBase(Product product, Seller seller, Manager manager) throws SQLException {
        String sql = "UPDATE Product SET quantity = ? WHERE name = ?";
        PreparedStatement st = conection.prepareStatement(sql);
        st.setInt(1, product.getQuantity());
        st.setString(2, product.getName());
        st.executeUpdate();

        String sql1 = "UPDATE Seler SET salescount = ?, salary = ? WHERE name = ?";
        PreparedStatement st1 = conection.prepareStatement(sql1);
        st1.setInt(1,seller.getsalesCount());
        st1.setFloat(2,seller.getIncome());
        st1.setString(3, seller.getName());
        st1.executeUpdate();

        String sql2 = "UPDATE Manager SET income = ? WHERE name = ?";
        PreparedStatement st2 = conection.prepareStatement(sql2);
        st2.setFloat(1, manager.getIncome());
        st2.setString(2, manager.getName());
        st2.executeUpdate();

        String sql3 = "UPDATE Boss SET income = ?";
        PreparedStatement st3 = conection.prepareStatement(sql3);
        st3.setFloat(1,     product.getPrice() - (product.getPrice()/100*seller.getCommision() + product.getPrice()/100*manager.getCommision()));
        st3.executeUpdate();
    }

    public void cleanDB() throws SQLException {
        String sql = "UPDATE Boss SET income = 0";
        PreparedStatement st = conection.prepareStatement(sql);
        st.executeUpdate();

        String sql1 = "UPDATE Manager SET income = 0";
        PreparedStatement st1 = conection.prepareStatement(sql1);
        st1.executeUpdate();

        String sql2 = "UPDATE Seler SET salary = 0, salescount = 0";
        PreparedStatement st2 = conection.prepareStatement(sql2);
        st2.executeUpdate();

        String sql3 = "UPDATE Product SET quantity = 1000";
        PreparedStatement st3 = conection.prepareStatement(sql3);
        st3.executeUpdate();
    }

    public List<Department> getDepartments() {
        return department;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public  List<Brand> getBrands() {
        return brands;
    }

    public List<Product> getProducts() {
        return products;
    }

//    public Connection getConnection(){
//        return conection;
//    }

    public  Database getDatabase(){
        return this;
    }
}

