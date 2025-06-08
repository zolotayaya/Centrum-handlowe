package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerDB {
    private static Connection connection;
    public static List<Manager> managers;

    public ManagerDB() {
        managers = new ArrayList<>();
        this.connection = Database.getConnection();
    }

    public void setManagersFromDB() throws SQLException {
        String sql = "SELECT * FROM Manager";
        PreparedStatement st = connection.prepareStatement(sql);
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
        List<Department> departments = DepartmentDB.getDepartments();
        if (depName == null) return null;
        for (Department dep : departments) {
            if (depName.trim().equalsIgnoreCase(dep.getName().trim())) {
                return dep;
            }
        }
        return null;
    }

    public List<Manager> getManagers() {
        return managers;
    }
    public void updaetIncome(Manager manager) {

    }
}
