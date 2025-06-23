package org.example.dao;
import org.example.database.Database;
import org.example.model.Brand;
import org.example.model.Product;
import org.example.model.Seller;

import java.time.LocalDate;
import java.util.*;
import java.sql.*;
import java.time.LocalDateTime;
/**
 * Klasa ReportingService odpowiada za generowanie różnych raportów dotyczących pracowników,
 * finansów oraz produktów.
 */
public class ReportingService {
    /**
            * Generuje raport pracowników z podziałem na stanowiska (Sprzedawca, Manager) oraz departamenty.
            * Raport zawiera szczegóły każdego pracownika oraz podsumowania na poziomie departamentu
     * i łączną liczbę pracowników według stanowisk.
            *
            * @return lista map reprezentujących raport, gdzie każda mapa to jeden wiersz raportu
     * @throws SQLException gdy wystąpi błąd podczas wykonywania zapytania do bazy danych
     */
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

                // Jeśli zmienia się departament, dodajemy wiersz podsumowujący dla poprzedniego
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

                // Dodanie szczegółowego wiersza z danymi pracownika
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("name", rs.getString("name"));
                row.put("department", department);
                row.put("income", rs.getFloat("income"));
                row.put("position", position);
                report.add(row);

                // Liczymy pracowników według stanowiska
                if (position.equals("Seller")) sellerCount++;
                else if (position.equals("Manager")) managerCount++;

                currentDepartment = department;
                currentDepartmentCount++;
            }

            // Dodajemy podsumowanie dla ostatniego departamentu
            if (currentDepartment != null) {
                Map<String, Object> summaryRow = new LinkedHashMap<>();
                summaryRow.put("id", "");
                summaryRow.put("name", "");
                summaryRow.put("department", currentDepartment + " (Total)");
                summaryRow.put("income", "");
                summaryRow.put("position", currentDepartmentCount + " employees");
                report.add(summaryRow);
            }

            // Dodajemy wiersz z sumarycznymi danymi o liczbie sprzedawców i managerów
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

    /**
     * Generuje podsumowanie finansowe na podstawie historii zakupów w określonym przedziale dat.
     * Raport zawiera szczegóły poszczególnych zakupów oraz sumaryczne przychody za dany okres.
     *
     * @param startDate data początkowa zakresu (włącznie)
     * @param endDate data końcowa zakresu (włącznie)
     * @return lista map reprezentujących raport finansowy, gdzie każda mapa to jeden rekord zakupu lub podsumowanie
     */
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
                    purchase.put("product", rs.getString("product_name"));
                    purchase.put("seller", rs.getString("seller_name"));
                    purchase.put("quantity", quantity);
                    purchase.put("price", price);
                    purchase.put("total", total);

                    report.add(purchase);
                    totalIncome += total;
                }
            }

            // Dodajemy wiersz podsumowujący łączne przychody
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

    /**
     * Generuje raport produktów wraz z informacjami o marce oraz ekspertach powiązanych z marką.
     *
     * @param productDB obiekt dostępu do danych produktów
     * @param brandDB obiekt dostępu do danych marek
     * @return lista map reprezentujących raport produktów, gdzie każda mapa zawiera dane jednego produktu
     */
    public List<Map<String, Object>> generateProductReport(ProductDB productDB, BrandDB brandDB) {
        List<Map<String, Object>> report = new ArrayList<>();

        for (Product product : productDB.getProducts()) {
            Map<String, Object> productData = new LinkedHashMap<>();
            Brand brand = product.getBrand();

            productData.put("product_id", product.getId());
            productData.put("product_name", product.getName());
            productData.put("price", product.getPrice());
            productData.put("quantity", product.getQuantity());
            productData.put("description", product.getDescription());

            if (brand != null) {
                productData.put("brand", brand.getName());
                productData.put("department", brand.getDepartment() != null ? brand.getDepartment().getName() : "N/A");

                List<String> expertNames = new ArrayList<>();
                for (Seller expert : brand.getExperts()) {
                    expertNames.add(expert.getName());
                }
                productData.put("experts", expertNames);
            } else {
                productData.put("brand", "Unknown");
                productData.put("department", "Unknown");
                productData.put("experts", Collections.emptyList());
            }

            report.add(productData);
        }

        return report;
    }

}
