package org.example.model;
import org.example.dao.ReviewDB;

import java.sql.*;
import java.text.SimpleDateFormat;

public class Review {
    private final int productId;
    private final String productName;
    private final int rating;
    private final String comment;
    private final Timestamp  date;
    private final String formattedDate;


    public Review(int productId, String productName, int rating, String comment, Timestamp date) {
        this.productId = productId;
        this.productName = productName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.formattedDate = date != null ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) :
                "N/A";
    }

    public static void saveReview(int productId, String name, int rating, String comment) throws SQLException {
        ReviewDB review = new ReviewDB();
        review.saveReviewInDB(new Review(productId, name, rating, comment, null));
        System.out.println("Review saved");
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getRating() { return rating; }
    public  String getComment() { return comment;};
    public String getFormattedDate() {return formattedDate;}
    public Timestamp getCreatedAt() {return date;}}
