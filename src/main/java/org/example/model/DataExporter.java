package org.example.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataExporter {

    public void exportEmployeeReport(List<Map<String, Object>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Name,Department,Income,Position\n");
            for (Map<String, Object> row : data) {
                writer.append(escapeCSV(row.getOrDefault("id", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("name", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("department", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("income", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("position", ""))).append("\n");
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
            writer.append("Product ID,Name,Price,Quantity,Description,Brand,Department\n");

            for (Map<String, Object> row : data) {
                writer.append(escapeCSV(row.getOrDefault("product_id", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("product_name", ""))).append(",");
                writer.append(String.format("%.2f", row.getOrDefault("price", 0.0))).append(",");
                writer.append(escapeCSV(row.getOrDefault("quantity", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("description", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("brand", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("department", ""))).append("\n");
            }
        }
    }


    private String escapeCSV(Object value) {
        String str = String.valueOf(value);
        if (str.contains(",") || str.contains("\"") || str.contains("\n")) {
            str = str.replace("\"", "\"\"");
            return "\"" + str + "\"";
        }
        return str;
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

