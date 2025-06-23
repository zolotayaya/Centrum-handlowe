package org.example.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Testy jednostkowe klasy {@link Review}.
 */
public class ReviewTest {

    /**
     * Testuje, czy konstruktor klasy {@link Review} poprawnie inicjalizuje pola,
     * a także czy data jest poprawnie formatowana na String.
     */
    @Test
    void constructor_korektna_inicjalizacja_pol() {
        int productId = 101;
        String productName = "Galaxy S24";
        int rating = 5;
        String comment = "Amazing phone!";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Review review = new Review(productId, productName, rating, comment, timestamp);

        assertEquals(productId, review.getProductId());
        assertEquals(productName, review.getProductName());
        assertEquals(rating, review.getRating());
        assertEquals(comment, review.getComment());
        assertEquals(timestamp, review.getCreatedAt());

        String expectedFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        assertEquals(expectedFormatted, review.getFormattedDate());
    }

    /**
     * Testuje, czy konstruktor klasy {@link Review} poprawnie obsługuje przypadek,
     * gdy znacznik czasu {@code createdAt} jest {@code null},
     * oraz czy metoda {@link Review#getFormattedDate()} zwraca "N/A".
     */
    @Test
    void constructor_obsluga_znacznika_o_zerowym_Czasie() {
        Review review = new Review(1, "iPhone 15", 4, "Good!", null);

        assertNull(review.getCreatedAt());
        assertEquals("N/A", review.getFormattedDate());
    }
}
