package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandDB {
    private static List<Brand> brands;
    private static Connection connection;
    public BrandDB() {
        brands = new ArrayList<Brand>();
        this.connection = Database.getConnection();
    }
    public void setBrandFromDB() throws SQLException {
        String sql = "SELECT * FROM Brand";
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            String depname = rs.getString("department");
            brands.add(new Brand(name, getDep(depname)));
        }
    }

    public Department getDep(String depName) {
        List<Department> department = DepartmentDB.getDepartments();
        if (depName == null) return null;
        for (Department dep : department) {
            if (depName.trim().equalsIgnoreCase(dep.getName().trim())) {
                return dep;
            }
        }
        return null;
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

    public static List<Brand> getBrands() {
        return brands;
    }
}
