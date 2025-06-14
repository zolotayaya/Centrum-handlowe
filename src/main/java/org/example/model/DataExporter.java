package org.example.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DataExporter {
    public void exportEmployeeReport(List<Map<String, Object>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Department,Position,Employee Count\n");
            for (Map<String, Object> row : data) {
                writer.append(row.get("department").toString()).append(",");
                writer.append(row.get("position").toString()).append(",");
                writer.append(row.get("employee_count").toString()).append("\n");
            }
        }
    }

    public void exportFinancialSummary(List<Map<String, Object>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Role,Name,Income\n");
            for (Map<String, Object> row : data) {
                writer.append(row.getOrDefault("role", "").toString()).append(",");
                writer.append(row.getOrDefault("name", "").toString()).append(",");
                writer.append(row.getOrDefault("income", "").toString()).append("\n");
            }
        }
    }

    public void exportProductReport(List<Map<String, Object>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Total Products,Average Price,Total Quantity\n");
            for (Map<String, Object> row : data) {
                writer.append(row.getOrDefault("total_products", "").toString()).append(",");
                writer.append(String.format("%.2f", row.getOrDefault("avg_price", 0.0))).append(",");
                writer.append(row.getOrDefault("total_quantity", "").toString()).append("\n");
            }
        }
    }

    public void exportSalesReport(List<Map<String, Object>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Department,Product Name,Quantity Sold,Total Sales\n");
            for (Map<String, Object> row : data) {
                writer.append(row.get("department").toString()).append(",");
                writer.append(row.get("productName").toString()).append(",");
                writer.append(row.get("quantitySold").toString()).append(",");
                writer.append(String.format("%.2f", row.get("totalSales"))).append("\n");
            }
        }
    }

    }

