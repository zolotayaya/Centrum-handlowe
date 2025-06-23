package org.example.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testy jednostkowe klasy {@link Product}.
 */
class ProductTest {

    private Brand brand;
    private Product product;

    /**
     * Inicjalizacja obiektu marki i produktu przed każdym testem.
     */
    @BeforeEach
    void setUp() {
        brand = new Brand("Apple", null);
        product = new Product("iPhone 14", 999.99f, 10, "Latest iPhone", brand, 1);
    }

    /**
     * Testuje, czy konstruktor poprawnie inicjalizuje wszystkie pola
     * oraz czy produkt zostaje automatycznie dodany do listy produktów marki.
     */
    @Test
    void constructor_inicjalizacja_pol_poprawnie() {
        assertEquals("iPhone 14", product.getName());
        assertEquals(999.99f, product.getPrice());
        assertEquals(10, product.getQuantity());
        assertEquals("Latest iPhone", product.getDescription());
        assertEquals(brand, product.getBrand());
        assertEquals(1, product.getId());

        assertTrue(brand.getProducts().contains(product), "Produkt powinien zostać dodany do marki");
    }

    /**
     * Testuje, czy metoda {@link Product#getDetails()} zwraca poprawny opis produktu w formie tekstu.
     */
    @Test
    void getDetails_zwraca_poprawny_String() {
        String expected = "Model: iPhone 14, Price: 999.99, Quantity: 10, Description: Latest iPhone";
        assertEquals(expected, product.getDetails());
    }

    /**
     * Testuje, czy metoda {@link Product#calculateFinalPrice(int)} poprawnie oblicza końcową cenę
     * na podstawie ilości zamówionych produktów.
     */
    @Test
    void calculateFinalPrice_zwraca_poprawna_wartosc_Sumy() {
        float total = product.calculateFinalPrice(3);
        assertEquals(2999.97f, total, 0.01f);
    }

    /**
     * Testuje, czy metoda {@link Product#updateQuantity(int)} poprawnie zmniejsza liczbę dostępnych produktów,
     * gdy ilość do zakupu jest dostępna.
     */
    @Test
    void updateQuantity_zmniejsza_ilosc_gdy_wystarczjaco_produktow() {
        boolean result = product.updateQuantity(4);
        assertTrue(result);
        assertEquals(6, product.getQuantity());
    }

    /**
     * Testuje, czy metoda {@link Product#updateQuantity(int)} zwraca fałsz,
     * gdy przekazana ilość to 0 lub liczba ujemna.
     */
    @Test
    void updateQuantity_Fail_gdy_ilosc_zero_lub_mniejZera() {
        assertFalse(product.updateQuantity(0));
        assertFalse(product.updateQuantity(-2));
        assertEquals(10, product.getQuantity());
    }

    /**
     * Testuje, czy metoda {@link Product#updateQuantity(int)} zwraca fałsz,
     * gdy próbujemy zakupić więcej produktów niż jest dostępnych.
     */
    @Test
    void updateQuantity_Fail_dgy_niewystarczajaco_jednostek() {
        assertFalse(product.updateQuantity(20));
        assertEquals(10, product.getQuantity());
    }
}
