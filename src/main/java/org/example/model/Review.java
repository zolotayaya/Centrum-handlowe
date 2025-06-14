package org.example.model;

import org.example.dao.ReviewDB;
import org.example.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Review {
    private int productId;
    private String productName;
    private int rating;
    private String comment;
    private String date;

    // Конструктор
    public Review(int productId, String productName, int rating, String comment, String date) {
        this.productId = productId;
        this.productName = productName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }


    public static void saveReview(int productId, String name, int rating, String comment) throws SQLException {
//        ReviewDB review = new ReviewDB(productId, name, rating, comment, null);
        String productName = getProductNameById(productId);
ReviewDB reviewDB = new ReviewDB();
//reviewDB.saveReview(Review);
//        String sql = "INSERT INTO Reviews (product_id, product_name, rating, comment) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = Database.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, productId);
//            stmt.setString(2, productName);
//            stmt.setInt(3, rating);
//            stmt.setString(4, comment);
//            stmt.executeUpdate();
//        }
    }

    private static String getProductNameById(int productId) throws SQLException {
        String sql = "SELECT name FROM products WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
            throw new SQLException("Product not found with id: " + productId);
        }
    }


    public static List<Review> getReviewsByProductId(int productId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Reviews WHERE product_id = ? ORDER BY created_at DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("created_at").toString()
                ));
            }
        }
        return reviews;
    }


    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getRating() { return rating; }
    public String getComment() { return comment;};
    public String getDate() { return date; }
}
