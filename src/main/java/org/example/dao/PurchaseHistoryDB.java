package org.example.dao;

import org.example.model.PurchaseRecord;
import org.example.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Klasa PurchaseHistoryDB odpowiada za dostęp do historii zakupów w bazie danych.
 */
public class PurchaseHistoryDB {
    /** Połączenie z bazą danych */
    private Connection connection;

    /**
     * Konstruktor inicjujący połączenie z bazą danych.
     */
    public PurchaseHistoryDB() {
        this.connection = Database.getConnection();
    }

    /**
     * Metoda zapisuje rekord zakupu do tabeli Purchase_History w bazie danych.
     *
     * @param purchaseRecord obiekt PurchaseRecord zawierający dane zakupu
     * @throws SQLException gdy wystąpi błąd podczas wykonywania zapytania SQL
     */
    public void updateHistory(PurchaseRecord purchaseRecord) throws SQLException {
        // Zapytanie SQL do wstawienia nowego rekordu do historii zakupów
        String sql = "INSERT INTO Purchase_History(product_name, seller_name, buyer_id, quantity, price) VALUES(?,?,?,?,?)";
        PreparedStatement st = connection.prepareStatement(sql);
        // Ustawienie wartości parametrów na podstawie obiektu PurchaseRecord
        st.setString(1, purchaseRecord.getProductName());
        st.setString(2, purchaseRecord.getSellerName());
        st.setInt(3, purchaseRecord.getBuyerID());
        st.setInt(4, purchaseRecord.getQuantity());
        st.setFloat(5, purchaseRecord.getPrice());
        st.executeUpdate(); // Wykonanie zapytania wstawiającego rekord do bazy danych
    }
}
