package org.example.dao;
import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SellerDB {
    private static List<Seller> sellers;
    private static Connection connection;
    public Random random;
    public SellerDB() {
        sellers = new ArrayList<Seller>();
        this.connection = Database.getConnection();
        this.random = new Random();
    }

    public void setSellersFromDB() throws SQLException {
        Random rand = new Random();
        String sql = "SELECT * FROM Sellers";
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String depname = rs.getString("department");
            float income = rs.getFloat("salary");
            float commision = rs.getFloat("commission");
            int salesCount = rs.getInt("salescount");
            float rating = rs.getFloat("rating");
            int experience = rs.getInt("experience_years");
            sellers.add(new Seller(id, name,getDep(depname),income, commision, salesCount,rating,experience));
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

    public void setExperience(int min, int max) throws SQLException {
        for (Seller seller : sellers) {
            String sql = "UPDATE Seller SET experience_years = ? WHERE name = ?";
            int experience = min + random.nextInt(max - min);
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, experience);
            st.setString(2, seller.getName());
        }
    }

    public static void updateSellerStats(Seller seller) throws SQLException {
        String sql = "UPDATE Sellers SET salary = ?, salescount = ? WHERE id = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setFloat(1, seller.getIncome());
        st.setInt(2, seller.getsalesCount());
        st.setInt(3, seller.getId());
        st.executeUpdate();
    }


    public static void updateSeller(Seller seller) throws SQLException{
        sellers.add(seller);
    }
    public static List<Seller> getSellers() {
        return sellers;
    }
    public static void deleteSellerById(int id) throws SQLException {
        String sql = "DELETE FROM Sellers WHERE id = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, id);
        st.executeUpdate();

        sellers.removeIf(s -> s.getId() == id);
    }
    public static void updateSellerRating(Seller seller) throws SQLException {
        String sql = "UPDATE sellers SET rating = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDouble(1, seller.getRating());
        stmt.setInt(2, seller.getId());
        stmt.executeUpdate();
        stmt.close();
    }
}
