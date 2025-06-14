package org.example.model;
import org.example.database.Database;

import javax.swing.*;
import java.util.*;
import java.sql.*;
import java.time.LocalDateTime;
public class ReportingService {
    public List<Map<String, Object>> employeeReportWithPosition() throws SQLException {
        List<Map<String, Object>> report = new ArrayList<>();
        String sql = "SELECT s.department, " +
                "CASE WHEN m.id IS NOT NULL THEN 'Managers' ELSE 'Sellers' END AS position, " +
                "COUNT(*) AS employee_count " +
                "FROM Sellers s " +
                "LEFT JOIN Managers m ON s.id = m.id " +
                "GROUP BY s.department, position " +
                "ORDER BY s.department, position";

        Connection conn = Database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("department", rs.getString("department"));
                row.put("position", rs.getString("position"));
                row.put("employee_count", rs.getInt("employee_count"));
                report.add(row);
            }
            }catch (SQLException e) {
            System.err.println("Error with connection: " + e.getMessage());
            e.printStackTrace();
        }
        return report;
    }

    public List<Map<String, Object>> generateFinancialSummary() throws SQLException {
        List<Map<String, Object>> report = new ArrayList<>();

        String bossSql = "SELECT name, income FROM Boss";
        String managerSql = "SELECT name, income FROM Managers";
        String totalIncomeSql = "SELECT SUM(income) AS total_income FROM Managers";

        try {
            Connection conn = Database.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(bossSql)) {
                if (rs.next()) {
                    Map<String, Object> bossData = new LinkedHashMap<>();
                    bossData.put("role", "Boss");
                    bossData.put("name", rs.getString("name"));
                    bossData.put("income", rs.getDouble("income"));
                    report.add(bossData);
                }
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(managerSql)) {
                while (rs.next()) {
                    Map<String, Object> managerData = new LinkedHashMap<>();
                    managerData.put("role", "Manager");
                    managerData.put("name", rs.getString("name"));
                    managerData.put("income", rs.getDouble("income"));
                    report.add(managerData);
                }
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(totalIncomeSql)) {
                if (rs.next()) {
                    Map<String, Object> totalIncomeData = new LinkedHashMap<>();
                    totalIncomeData.put("role", "Total Managers");
                    totalIncomeData.put("income", rs.getDouble("total_income"));
                    report.add(totalIncomeData);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error with connection: " + e.getMessage());
            e.printStackTrace();
        }
        return report;
    }

    public List<Map<String, Object>> generateProductReport() throws SQLException {
        List<Map<String, Object>> report = new ArrayList<>();
        String sql = "SELECT COUNT(*) AS total_products, AVG(price) AS avg_price, SUM(quantity) AS total_quantity FROM Products";
        Connection conn = Database.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                Map<String, Object> productData = new LinkedHashMap<>();
                productData.put("total_products", rs.getInt("total_products"));
                productData.put("avg_price", rs.getDouble("avg_price"));
                productData.put("total_quantity", rs.getInt("total_quantity"));
                report.add(productData);
            }
        }
        return report;
    }

    public List<Map<String, Object>> generateSalesReport(LocalDateTime from, LocalDateTime to) throws SQLException {
        List<Map<String, Object>> reportData = new ArrayList<>();
        String sql = "SELECT s.department, ph.product_name, SUM(ph.quantity) AS total_quantity, " +
                "SUM(ph.price * ph.quantity) AS total_sales " +
                "FROM purchase_history ph " +
                "JOIN Seller s ON ph.seller_name = s.name " +
                "WHERE ph.purchase_date BETWEEN ? AND ? " +
                "GROUP BY s.department, ph.product_name " +
                "ORDER BY s.department, total_sales DESC";
        Connection conn = Database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(from));
            stmt.setTimestamp(2, Timestamp.valueOf(to));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("department", rs.getString("department"));
                row.put("productName", rs.getString("product_name"));
                row.put("quantitySold", rs.getInt("total_quantity"));
                row.put("totalSales", rs.getDouble("total_sales"));
                reportData.add(row);
            }
        }
        return reportData;
    }
}
