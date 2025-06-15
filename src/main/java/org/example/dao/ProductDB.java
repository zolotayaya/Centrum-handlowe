package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Brand;
import org.example.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDB {
    private static List<Product> products;
    private static Connection connection;
    public ProductDB() {
        products = new ArrayList<Product>();
        connection = Database.getConnection();
}
    public void setProductsFromDB(BrandDB brandDB) throws SQLException {
        String sql = "SELECT * FROM Products";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                String brand = rs.getString("brand");
                Brand brand1 = getBrand(brand, brandDB);
                if (brand1 == null) {
                    System.out.println(" Brand nie znaleziono: " + brand);
                    continue;
                }
                products.add(new Product(name, price, quantity, description, getBrand(brand, brandDB),id));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Department getDep(String depName) {
        List<Department> departments = DepartmentDB.getDepartments();
        if (depName == null) return null;
        for (Department dep : departments) {
            if (depName.trim().equalsIgnoreCase(dep.getName().trim())) {
                return dep;
            }
        }
        return null;
    }

    public Brand getBrand(String brandName, BrandDB brandDB) {
        List<Brand> brands = brandDB.getBrands();
        if (brandName == null) return null;
        for (Brand brand : brands) {
            if (brandName.trim().equalsIgnoreCase(brand.getName().trim())) {
                return brand;
            }
        }
        return null;
    }

    public void updateQuantityInDB(Product product, int quantity) throws SQLException {
        String sql = "UPDATE Products SET quantity = ? WHERE name = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, quantity);
        st.setString(2, product.getName());
        st.executeUpdate();
        System.out.println("Quantity updated in DB: " + quantity);
    }

    public List<Product> getProducts() {
        return products;
    }
}