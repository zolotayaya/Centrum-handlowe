package org.example.model;

import org.example.dao.PurchaseHistoryDB;

import java.sql.SQLException;
import java.util.*;

/**
 * Klasa PurchaseHistory zarządza historią zakupów.
 * Przechowuje listę rekordów zakupów oraz synchronizuje je z bazą danych.
 */
public class PurchaseHistory {
    private List<PurchaseRecord> purchases = new ArrayList<>();
    private PurchaseHistoryDB purchaseHistoryDB = new PurchaseHistoryDB(); // Obiekt do operacji na bazie danych historii zakupów

    /**
     * Dodaje nowy rekord zakupu do listy i aktualizuje bazę danych.
     *
     * @param record nowy rekord zakupu
     * @throws SQLException w przypadku błędów podczas aktualizacji bazy danych
     */
    public void addPurchase(PurchaseRecord record) throws SQLException {
        purchases.add(record);
        purchaseHistoryDB.updateHistory(record);
    }

    /**
     * Wyświetla wszystkie rekordy zakupów na konsoli.
     */
    public void displayHistory() {
        for (PurchaseRecord pr : purchases) {
            System.out.println(pr);
        }
    }

    /**
     * Zwraca listę wszystkich rekordów zakupów przechowywanych w pamięci.
     *
     * @return lista rekordów zakupów
     */
    public List<PurchaseRecord> getAll() {
        return purchases;
    }
}
