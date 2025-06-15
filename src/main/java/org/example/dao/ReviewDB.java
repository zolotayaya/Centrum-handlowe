package org.example.dao;

import org.example.database.Database;
import org.example.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDB {
    private  Connection connection;
    private List<Review> reviews;
    public ReviewDB() {
        this.connection = Database.getConnection();
        reviews = new ArrayList<Review>();
    }

    public void saveReviewInDB(Review review) throws SQLException {
        String sql = "INSERT INTO Reviews(product_id, product_name, rating, comment) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, review.getProductId());
            stmt.setString(2, review.getProductName());
            stmt.setInt(3, review.getRating());
            System.out.println("Rating: " + review.getRating());
            stmt.setString(4, review.getComment());
            System.out.println("Comment: " + review.getComment());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error with saving a comment: " + e.getMessage());
            throw e;
        }
    }

    private  String getProductNameById(int productId) throws SQLException {
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
    public List<Review> getReviewsByProductId(int productId) throws SQLException {

        String sql = "SELECT * FROM Reviews WHERE product_id = ? ORDER BY created_at DESC";
        Connection conn = Database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        return reviews;
    }
}
