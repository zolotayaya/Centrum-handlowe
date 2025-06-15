package org.example.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DataExporter {

    public void exportEmployeeReport(List<Map<String, Object>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Name,Department,Income,Position\n");
            for (Map<String, Object> row : data) {
                writer.append(String.valueOf(row.getOrDefault("id", ""))).append(",");
                writer.append(String.valueOf(row.getOrDefault("name", ""))).append(",");
                writer.append(String.valueOf(row.getOrDefault("department", ""))).append(",");
                writer.append(String.valueOf(row.getOrDefault("income", ""))).append(",");
                writer.append(String.valueOf(row.getOrDefault("position", ""))).append("\n");
            }
        }
    }

    public void exportFinancialSummary(List<Map<String, Object>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.append("Date,Product,Seller,Quantity,Price,Total\n");

            for (Map<String, Object> row : data) {
                writer.append(row.getOrDefault("date", "").toString()).append(",");
                writer.append(row.getOrDefault("product", "").toString()).append(",");
                writer.append(row.getOrDefault("seller", "").toString()).append(",");
                writer.append(row.getOrDefault("quantity", "").toString()).append(",");
                writer.append(row.getOrDefault("price", "").toString()).append(",");
                writer.append(row.getOrDefault("total", "").toString()).append("\n");
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

