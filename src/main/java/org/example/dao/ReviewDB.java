package org.example.dao;

import org.example.Review;
import org.example.database.Database;
import org.example.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDB {
//    private Connection connection;
//    private List<Review> reviews;
//    public ReviewDB() {
//        this.connection = Database.getConnection();
//        reviews = new ArrayList<Review>();
//    }
//
//    public void saveReview(Review review) throws SQLException {
//        String sql = "INSERT INTO Reviews VALUES (?, ?, ?, ?)";
//        PreparedStatement st = connection.prepareStatement(sql);
//        stmt.setInt(1, productId);
//        stmt.setString(2, productName);
//        stmt.setInt(3, rating);
//        stmt.setString(4, comment);
//        stmt.executeUpdate();
//    }
}
