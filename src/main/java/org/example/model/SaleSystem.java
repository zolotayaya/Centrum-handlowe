package org.example.model;

import java.sql.SQLException;

/**
 * Klasa SaleSystem zarządza procesem sprzedaży produktów,
 * w tym rejestruje historię zakupów.
 */
public class SaleSystem {
    private PurchaseHistory purchaseHistory; // Przechowuje historię wszystkich zakupów

    /**
     * Konstruktor inicjalizujący historię zakupów.
     */
    public SaleSystem() {
        this.purchaseHistory = new PurchaseHistory();
    }

    /**
     * Przetwarza sprzedaż produktu.
     * Sprawdza dostępność towaru, wykonuje sprzedaż przez sprzedawcę,
     * tworzy rekord zakupu i zapisuje go w historii oraz bazie danych.
     *
     * @param product  sprzedawany produkt
     * @param seller   sprzedawca realizujący sprzedaż
     * @param quantity ilość sprzedawanego produktu
     * @param buyerID  identyfikator kupującego
     * @throws SQLException w przypadku błędów związanych z bazą danych
     */
    public void processPurchase(Product product, Seller seller, int quantity, int buyerID) throws SQLException {
        if (product.getQuantity() < quantity) {
            System.out.println("Not enough product.");
            return;
        }
        seller.saleProduct(product, quantity); // Realizacja sprzedaży przez sprzedawcę
        PurchaseRecord record = new PurchaseRecord(product, seller, quantity, buyerID);
        purchaseHistory.addPurchase(record); // Dodanie rekordu do historii i zapis w bazie danych

        System.out.println("The sale ended successful!");
    }

    /**
     * Zwraca obiekt historii zakupów.
     *
     * @return obiekt PurchaseHistory zawierający wszystkie rekordy zakupów
     */
    public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
    }
}
