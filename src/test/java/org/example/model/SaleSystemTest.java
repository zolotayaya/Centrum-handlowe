package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe klasy {@link SaleSystem}.
 */
public class SaleSystemTest {

    private SaleSystem saleSystem;
    private Product product;
    private Seller seller;
    private Department department;

    /**
     * Inicjalizacja obiektów testowych przed każdym testem.
     */
    @BeforeEach
    void setUp() {
        saleSystem = new SaleSystem();
        department = new Department("Electronics");
        product = new Product("iPhone 15", 1200.0f, 10, "Latest model", new Brand("Apple", department), 1);
        seller = new Seller(1, "Alice", department, 3000.0f, 10.0f, 0, 4.5f, 3);
    }

    /**
     * Testuje, czy metoda {@link SaleSystem#getPurchaseHistory()} zwraca poprawnie historię sprzedaży,
     * a także czy ta historia jest początkowo pusta.
     */
    @Test
    void getPurchaseHistory_zwraca_poprawnie_historie_Srzedarzy() {
        assertNotNull(saleSystem.getPurchaseHistory());
        assertTrue(saleSystem.getPurchaseHistory().getAll().isEmpty());
    }
}
