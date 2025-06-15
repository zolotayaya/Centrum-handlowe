package org.example.model;
import org.example.database.Database;

import javax.swing.*;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.*;
import java.sql.*;
import java.time.LocalDateTime;
public class ReportingService {
    public List<Map<String, Object>> employeeReportWithPosition() throws SQLException {
        List<Map<String, Object>> report = new ArrayList<>();

        String sql = "SELECT e.id, e.name, e.department, e.income, e.position FROM (" +
                " SELECT id, name, department, salary AS income, 'Seller' AS position FROM Sellers " +
                " UNION ALL " +
                " SELECT id, name, department, income, 'Manager' AS position FROM Managers" +
                ") e ORDER BY e.department, e.position";

        Map<String, Integer> departmentCounts = new LinkedHashMap<>();
        int sellerCount = 0;
        int managerCount = 0;
        String currentDepartment = null;
        int currentDepartmentCount = 0;
        Connection conn = Database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String department = rs.getString("department");
                String position = rs.getString("position");

                if (!department.equals(currentDepartment) && currentDepartment != null) {

                    Map<String, Object> summaryRow = new LinkedHashMap<>();
                    summaryRow.put("id", "");
                    summaryRow.put("name", "");
                    summaryRow.put("department", currentDepartment + " (Total)");
                    summaryRow.put("income", "");
                    summaryRow.put("position", currentDepartmentCount + " employees");
                    report.add(summaryRow);

                    currentDepartmentCount = 0;
                }

                Map<String, Object> row = new LinkedHashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("name", rs.getString("name"));
                row.put("department", department);
                row.put("income", rs.getFloat("income"));
                row.put("position", position);
                report.add(row);


                if (position.equals("Seller")) sellerCount++;
                else if (position.equals("Manager")) managerCount++;

                currentDepartment = department;
                currentDepartmentCount++;
            }


            if (currentDepartment != null) {
                Map<String, Object> summaryRow = new LinkedHashMap<>();
                summaryRow.put("id", "");
                summaryRow.put("name", "");
                summaryRow.put("department", currentDepartment + " (Total)");
                summaryRow.put("income", "");
                summaryRow.put("position", currentDepartmentCount + " employees");
                report.add(summaryRow);
            }


            Map<String, Object> totalRow = new LinkedHashMap<>();
            totalRow.put("id", "");
            totalRow.put("name", "");
            totalRow.put("department", "TOTAL");
            totalRow.put("income", "");
            totalRow.put("position", "Sellers: " + sellerCount + ", Managers: " + managerCount);
            report.add(totalRow);

        } catch (SQLException e) {
            System.err.println("Error fetching employees: " + e.getMessage());
            e.printStackTrace();
        }

        return report;
    }


    public List<Map<String, Object>> generateFinancialSummary(LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> report = new ArrayList<>();
        float totalIncome = 0f;

        String sql = "SELECT purchase_date, product_name, seller_name, quantity, price " +
                "FROM Purchase_History " +
                "WHERE purchase_date BETWEEN ? AND ? " +
                "ORDER BY purchase_date";
        Connection conn = Database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
            stmt.setTimestamp(2, Timestamp.valueOf(endDate.plusDays(1).atStartOfDay())); // включительно до конца дня

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    float total = quantity * price;

                    Map<String, Object> purchase = new LinkedHashMap<>();
                    purchase.put("date", rs.getTimestamp("purchase_date").toLocalDateTime().toString());
                    purchase.put("product",rs.getString("product_name"));
                    purchase.put("seller", rs.getString("seller_name"));
                    purchase.put("quantity", quantity);
                    purchase.put("price", price);
                    purchase.put("total", total);

                    report.add(purchase);
                    totalIncome += total;
                }
            }

            // Add summary row
            Map<String, Object> totalRow = new LinkedHashMap<>();
            totalRow.put("date", "");
            totalRow.put("seller", "TOTAL INCOME");
            totalRow.put("quantity", "");
            totalRow.put("price", "");
            totalRow.put("total", totalIncome);
            report.add(totalRow);

        } catch (SQLException e) {
            System.err.println("Error generating financial summary: " + e.getMessage());
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
