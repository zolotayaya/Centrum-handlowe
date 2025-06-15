package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Manager;
import org.example.model.Seller;

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
        String sql = "SELECT * FROM Managers";
        PreparedStatement st = connection.prepareStatement(sql);
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

    public static void updateManagerIncome(Manager manager) throws SQLException {
        String sql = "UPDATE Managers SET income = ? WHERE id = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setFloat(1, manager.getIncome());
        st.setInt(2, manager.getId());
        st.executeUpdate();
    }


    public List<Manager> getManagers() {
        return managers;
    }

    public static void promoteSellerToManager(Seller seller) throws SQLException {
        Department department = seller.getDepartment();
        if (department == null) return;

        Manager oldManager = department.getManager();
        if (oldManager != null) {
            String deleteOldManagerSQL = "DELETE FROM Managers WHERE id = ?";
            PreparedStatement delStmt = connection.prepareStatement(deleteOldManagerSQL);
            delStmt.setInt(1, oldManager.getId());
            delStmt.executeUpdate();

            managers.removeIf(m -> m.getId() == oldManager.getId());
            System.out.println("Old manager " + oldManager.getName() + " removed from DB.");
        }

        Manager newManager = new Manager(
                seller.getId(),
                seller.getName(),
                department,
                seller.getIncome(),
                seller.getCommision(),
                seller.getExperience()
        );

        String insertSQL = "INSERT INTO Managers (id, name, department, income, commission, experience) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
        insertStmt.setInt(1, newManager.getId());
        insertStmt.setString(2, newManager.getName());
        insertStmt.setString(3, department.getName());
        insertStmt.setFloat(4, newManager.getIncome());
        insertStmt.setFloat(5, newManager.getCommision());
        insertStmt.setInt(6, newManager.getExperience());
        insertStmt.executeUpdate();

        managers.add(newManager);
        System.out.println("New manager " + newManager.getName() + " inserted into DB.");


        String deleteSellerSQL = "DELETE FROM Sellers WHERE id = ?";
        PreparedStatement deleteStmt = connection.prepareStatement(deleteSellerSQL);
        deleteStmt.setInt(1, seller.getId());
        deleteStmt.executeUpdate();

        SellerDB.getSellers().removeIf(s -> s.getId() == seller.getId());
        System.out.println("Old seller " + seller.getName() + " removed from DB.");
    }

    public static void insertManager(Manager manager) throws SQLException {
        String sql = "INSERT INTO Managers ( name, department, income, commission, experience) VALUES ( ?, ?, ?, ?, ?)";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, manager.getName());
        st.setString(2, manager.getDepartment().getName());
        st.setFloat(3, manager.getIncome());
        st.setFloat(4, manager.getCommision());
        st.setInt(5, manager.getExperience());
        st.executeUpdate();
        managers.add(manager);
    }

    public static void deleteManagerById(int id) throws SQLException {
        String sql = "DELETE FROM Managers WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();

        managers.removeIf(manager -> manager.getId() == id);
    }
}
