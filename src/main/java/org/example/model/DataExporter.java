package org.example.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Klasa odpowiedzialna za eksportowanie danych do plików w formacie CSV.
 */
public class DataExporter {

    /**
     * Eksportuje raport pracowników do pliku CSV.
     *
     * @param data     lista map zawierających dane pracowników (klucz-wartość)
     * @param filePath ścieżka do pliku, do którego zostanie zapisany raport
     * @throws IOException jeśli wystąpi błąd podczas zapisu do pliku
     */
    public void exportEmployeeReport(List<Map<String, Object>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Name,Department,Income,Position\n"); // Nagłówek kolumn
            for (Map<String, Object> row : data) {
                writer.append(escapeCSV(row.getOrDefault("id", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("name", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("department", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("income", ""))).append(",");
                writer.append(escapeCSV(row.getOrDefault("position", ""))).append("\n");
            }
        }
    }

    /**
     * Eksportuje podsumowanie finansowe do pliku CSV.
     *
     * @param data     lista map zawierających dane finansowe (klucz-wartość)
     * @param filePath ścieżka do pliku, do którego zostanie zapisane podsumowanie
     * @throws IOException jeśli wystąpi błąd podczas zapisu do pliku
     */
    public void exportFinancialSummary(List<Map<String, Object>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Date,Product,Seller,Quantity,Price,Total\n"); // Nagłówek kolumn
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

    /**
     * Eksportuje raport produktów do pliku CSV.
     *
     * @param data     lista map zawierających dane produktów (klucz-wartość)
     * @param filePath ścieżka do pliku, do którego zostanie zapisany raport
     * @throws IOException jeśli wystąpi błąd podczas zapisu do pliku
     */
    public void exportProductReport(List<Map<String, Object>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Product ID,Name,Price,Quantity,Description,Brand,Department\n"); // Nagłówek kolumn
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

    /**
     * Pomocnicza metoda do poprawnego formatowania tekstów w formacie CSV.
     * Zabezpiecza dane zawierające przecinki, cudzysłowy lub znaki nowej linii,
     * odpowiednio je escapując.
     *
     * @param value wartość do sformatowania
     * @return sformatowany łańcuch znaków gotowy do zapisu w CSV
     */
    private String escapeCSV(Object value) {
        String str = String.valueOf(value);
        if (str.contains(",") || str.contains("\"") || str.contains("\n")) {
            str = str.replace("\"", "\"\""); // Podwaja cudzysłowy wewnątrz tekstu
            return "\"" + str + "\"";        // Otacza tekst cudzysłowami
        }
        return str;
    }
}
