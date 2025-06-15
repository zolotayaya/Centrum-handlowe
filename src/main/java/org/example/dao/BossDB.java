package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Boss;
import org.example.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BossDB {
    private static Boss instance;
    private List<Employee> allEmployees;
    private List<Department> allDepartment;
    private static Connection connection;

    public BossDB() {
        this.allEmployees = new ArrayList<>();
        this.allDepartment = new ArrayList<>();
        this.connection = Database.getConnection();
    }

    public static Boss setBossFromDB() throws SQLException {
        if (instance == null) {
            try{
                PreparedStatement stmt = connection.prepareStatement("SELECT name, income FROM Boss WHERE id = 1");
                ResultSet rs = stmt.executeQuery(

                );

                if (rs.next()) {
                    String name = rs.getString("name");
                    float income = rs.getFloat("income");
                    instance = new Boss(name, income);
                } else {
                    instance = new Boss("Default Boss", 0);

                }
            } catch (SQLException e) {
                e.printStackTrace();
                instance = new Boss("Default Boss", 0);
            }
        }
        return instance;
    }
    public static Boss getBoss() {
        return instance;
    }


    public  void updateIncomeInDatabase() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE boss SET income = ? WHERE id = 0");
        stmt.setFloat(1, instance.getIncome());
        stmt.executeUpdate();
    }
    public float CalculateTotalIncomeFromHistory(LocalDate startDate, LocalDate endDate) {
        float totalIncome = 0f;

        String sql = "SELECT quantity, price FROM Purchase_History WHERE purchase_date BETWEEN ? AND ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    totalIncome += quantity * price;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching income from DB: " + e.getMessage());
        }

        return totalIncome;
    }

}