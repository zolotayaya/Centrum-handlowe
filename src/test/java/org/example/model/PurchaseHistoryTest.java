package org.example.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHistoryTest {

    private PurchaseHistory purchaseHistory;
    private Product product;
    private Seller seller;
    private Department department;
    private PurchaseRecord record;

    @BeforeEach
    void setUp() {
        department = new Department("Electronics");
        product = new Product("MacBook Pro", 2000.0f, 5, "Laptop", new Brand("Apple", department), 100);
        seller = new Seller(1, "Bob", department, 2500.0f, 12.0f, 5, 4.6f, 3);
        purchaseHistory = new PurchaseHistory();

        record = new PurchaseRecord(product, seller, 1, 101);
    }


    @Test
    void getAll_zwraca_pusta_Liste() {
        assertTrue(purchaseHistory.getAll().isEmpty());
    }

}
