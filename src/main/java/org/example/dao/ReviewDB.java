package org.example.dao;

import org.example.database.Database;
import org.example.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa ReviewDB odpowiada za dostęp do recenzji w bazie danych.
 */
public class ReviewDB {
    /** Połączenie z bazą danych */
    private Connection connection;

    /** Lista przechowująca recenzje */
    private List<Review> reviews;

    /**
     * Konstruktor inicjujący połączenie z bazą danych i przygotowujący listę recenzji.
     */
    public ReviewDB() {
        this.connection = Database.getConnection();
        reviews = new ArrayList<Review>();
    }

    /**
     * Zapisuje recenzję do bazy danych.
     *
     * @param review obiekt Review zawierający dane recenzji
     * @throws SQLException gdy wystąpi błąd podczas wykonywania zapytania SQL
     */
    public void saveReviewInDB(Review review) throws SQLException {
        String sql = "INSERT INTO Reviews(product_id, product_name, rating, comment) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, review.getProductId());
            stmt.setString(2, review.getProductName());
            stmt.setInt(3, review.getRating());
            System.out.println("Rating: " + review.getRating());
            stmt.setString(4, review.getComment());
            System.out.println("Comment: " + review.getComment());
            stmt.executeUpdate(); // wykonanie zapytania INSERT
        } catch (SQLException e) {
            System.err.println("Error with saving a comment: " + e.getMessage());
            throw e; // przekazanie wyjątku dalej
        }
    }

    /**
     * Prywatna metoda pomocnicza pobierająca nazwę produktu na podstawie jego ID.
     *
     * @param productId identyfikator produktu
     * @return nazwa produktu
     * @throws SQLException gdy produkt o podanym ID nie zostanie znaleziony lub wystąpi błąd bazy danych
     */
    private String getProductNameById(int productId) throws SQLException {
        String sql = "SELECT name FROM Products WHERE id = ?";
        Connection conn = Database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
            throw new SQLException("Product not found with id: " + productId);
        }
    }

    /**
     * Pobiera listę recenzji dla danego produktu, posortowaną malejąco według daty utworzenia.
     *
     * @param productId identyfikator produktu, dla którego pobierane są recenzje
     * @return lista recenzji
     * @throws SQLException gdy wystąpi błąd podczas pobierania danych z bazy
     */
    public List<Review> getReviewsByProductId(int productId) throws SQLException {
        String sql = "SELECT * FROM Reviews WHERE product_id = ? ORDER BY created_at DESC";
        Connection conn = Database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId); // ustawienie ID produktu
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // dodanie recenzji do listy na podstawie danych z bazy
                reviews.add(new Review(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        return reviews; // zwrócenie listy recenzji
    }
}
