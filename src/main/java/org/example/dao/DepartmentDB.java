package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDB {
    private static List<Department> department;
    private static Connection connection;
    public DepartmentDB() {
        department = new ArrayList<>();
        this.connection = Database.getConnection();
    }
    public void setDepartmentsFromDB() throws SQLException {
        String sql = "SELECT * FROM Department";
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            department.add(new Department(rs.getString("name")));
        }
    }
    public static List<Department> getDepartments() {
        return department;
    }
}
