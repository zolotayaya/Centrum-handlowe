package org.example.model;

import org.example.dao.ReviewDB;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Klasa Review reprezentuje recenzję produktu.
 * Zawiera informacje o produkcie, ocenie, komentarzu oraz dacie recenzji.
 */
public class Review {
    private final int productId;
    private final String productName;
    private final int rating;
    private final String comment;
    private final Timestamp date;
    private final String formattedDate;

    /**
     * Konstruktor tworzący obiekt recenzji.
     *
     * @param productId   identyfikator produktu
     * @param productName nazwa produktu
     * @param rating      ocena produktu (np. w skali 1-5)
     * @param comment     komentarz recenzenta
     * @param date        data utworzenia recenzji (Timestamp)
     */
    public Review(int productId, String productName, int rating, String comment, Timestamp date) {
        this.productId = productId;
        this.productName = productName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        // Formatuje datę do "yyyy-MM-dd HH:mm:ss" lub ustawia "N/A", jeśli data jest null
        this.formattedDate = date != null ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) :
                "N/A";
    }

    /**
     * Zapisuje recenzję w bazie danych.
     *
     * @param productId   identyfikator produktu
     * @param name        nazwa produktu
     * @param rating      ocena produktu
     * @param comment     komentarz
     * @throws SQLException w przypadku błędu podczas zapisu do bazy danych
     */
    public static void saveReview(int productId, String name, int rating, String comment) throws SQLException {
        ReviewDB reviewDB = new ReviewDB();
        reviewDB.saveReviewInDB(new Review(productId, name, rating, comment, null));
        System.out.println("Review saved");
    }

    // Gettery do pobierania wartości pól

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public Timestamp getCreatedAt() {
        return date;
    }
}
