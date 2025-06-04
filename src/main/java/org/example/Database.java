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
        connection();  // викликаємо підключення один раз при створенні об’єкта
        // ініціалізація списків
        sellers = new ArrayList<>();
        managers = new ArrayList<>();
        brands = new ArrayList<>();
        department = new ArrayList<>();
        products = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public static Database getInstance() { //Singleton
        if (instance == null) {
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
            managers.add(new Manager(id, name, getDep(depname), income, commission, experience));
        }
    }

    public void setSellersFromDB() throws SQLException {
        String sql = "SELECT * FROM Seler";
        PreparedStatement st = conection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String departmentname = rs.getString("department");
            float income = rs.getFloat("salary");
            float commision = rs.getFloat("commission");
            int salesCount = rs.getInt("salesCount");
            float rating = rs.getFloat("rating");
            int experience = rs.getInt("experience_years");
            for (Department dep : department) {
                if (departmentname.equals(dep.getName())) {
                    sellers.add(new Seller(id, name, dep, income, commision, salesCount, rating, experience));
                }
            }
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


    public Department getDep(String depName) {
        if (depName == null) return null;
        for (Department dep : department) {
            if (depName.trim().equalsIgnoreCase(dep.getName().trim())) {
                return dep;
            }
        }
        return null;
    }


    public void updateQuantityInDB(Product product, int quantity) throws SQLException {
        String sql = "UPDATE Product SET quantity = ? WHERE name = ?";
        PreparedStatement st = conection.prepareStatement(sql);
        st.setInt(1, quantity);
        st.setString(2, product.getName());
        st.executeUpdate();
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


    public List<Department> getDepartments() {
        return department;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public void setExperience(int min, int max) throws SQLException {
        Random rand = new Random();
        String sql = "UPDATE Seler SET experience_years = ? WHERE name = ?";
        PreparedStatement ps = conection.prepareStatement(sql);
        for (Seller seller : sellers) {
            int experience = min + rand.nextInt(max - min + 1);
            ps.setInt(1, experience);
            ps.setString(2, seller.getName());
            ps.executeUpdate();
        }
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

    public Connection getConnection(){
        return conection;
    }
    public  Database getDatabase(){
        return this;
    }
}

