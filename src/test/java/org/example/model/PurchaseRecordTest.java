package org.example.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class PurchaseRecordTest {

    private Product product;
    private Seller seller;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department("Electronics");
        product = new Product("PlayStation 5", 499.99f, 5, "Gaming console", new Brand("Sony", department), 101);
        seller = new Seller(1, "Alice", department, 2000.0f, 10.0f, 3, 4.8f, 2);
    }

    @Test
    void constructor_korektna_inicjalizacja_pol() {
        int quantity = 2;
        int buyerId = 999;

        PurchaseRecord record = new PurchaseRecord(product, seller, quantity, buyerId);

        assertEquals("PlayStation 5", record.getProductName());
        assertEquals("Alice", record.getSellerName());
        assertEquals(quantity, record.getQuantity());
        assertEquals(buyerId, record.getBuyerID());
        assertEquals(499.99f, record.getPrice());
        assertEquals(seller, record.getSeller());
    }

    @Test
    void getProductName_zwraca_poprawne_imie() {
        PurchaseRecord record = new PurchaseRecord(product, seller, 1, 123);
        assertEquals("PlayStation 5", record.getProductName());
    }

    @Test
    void getSeller_zwraca_poprawnie_Sprzedawce() {
        PurchaseRecord record = new PurchaseRecord(product, seller, 1, 123);
        assertSame(seller, record.getSeller());
    }
}
