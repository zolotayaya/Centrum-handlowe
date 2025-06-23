package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 * Testy jednostkowe klasy {@link Seller}.
 */
class SellerTest {
    private Department department;
    private Seller seller;
    private Product product;
    private Brand brand;

    /**
     * Inicjalizacja przykładowych obiektów przed każdym testem.
     */
    @BeforeEach
    void setUp() {
        department = new Department("Sales");
        brand = new Brand("Samsung", department);
        product = new Product("Galaxy S24", 800.0f, 10, "Smartphone", brand, 1);
        seller = new Seller(1, "Ivan", department, 2000f, 10f, 0, 4.5f, 3);
    }

    /**
     * Testuje, czy konstruktor poprawnie inicjalizuje pola sprzedawcy,
     * oraz czy sprzedawca jest dodany do działu.
     */
    @Test
    void constructor_inicjalizowany_poprawnie() {
        assertEquals("Ivan", seller.getName());
        assertEquals(2000f, seller.getIncome());
        assertEquals(10f, seller.getCommision());
        assertEquals(4.5, seller.getRating(), 0.01);
        assertEquals(department, seller.getDepartment());
        assertTrue(department.getEmployees().contains(seller));
    }

    /**
     * Testuje, czy metoda {@link Seller#checkPromotionCondition()} zwraca false,
     * gdy liczba sprzedaży jest niewystarczająca do awansu.
     */
    @Test
    void checkPromotionCondition_zwraca_False_gdy_niewystarczajaco_sprzedarzow() {
        assertFalse(seller.checkPromotionCondition());
    }

    /**
     * Testuje, czy metoda {@link Seller#checkPromotionCondition()} zwraca true,
     * gdy liczba sprzedaży jest wystarczająca do awansu.
     */
    @Test
    void checkPromotionCondition_zwraca_True_gdy_wystarczajaco_sprzedarzow() {
        Seller seller = new Seller(1, "Antuan", department, 2000f, 10f, 6, 4.5f, 3);
        assertTrue(seller.checkPromotionCondition());
    }

    /**
     * Testuje, czy metoda {@link Seller#addRating(int)} poprawnie uśrednia oceny.
     */
    @Test
    void addRating_usrednia_poprawnie() {
        seller.addRating(5);
        assertEquals((4.5 + 5) / 2.0, seller.getRating(), 0.01);
    }

    /**
     * Testuje, czy metoda {@link Seller#canSellNow()} zwraca false,
     * gdy sprzedawca przekroczył limit sprzedaży oparty na ocenie.
     */
    @Test
    void canSellNow_limit_oparty_na_Ocenie() {
        for (int i = 0; i < 5; i++) {
            seller.recordSale();
        }
        assertFalse(seller.canSellNow());
    }

    /**
     * Testuje, czy metoda {@link Seller#canSellNow()} pozwala sprzedawać,
     * gdy sprzedawca nie przekroczył limitu.
     */
    @Test
    void canSellNow_pozwakla_na_sprzedarz_jesli_dolny_limit() {
        seller.recordSale();
        assertTrue(seller.canSellNow());
    }

    /**
     * Testuje, czy metoda {@link Seller#setBrand(Brand)} ustawia markę
     * oraz dodaje sprzedawcę jako eksperta, jeśli jeszcze nim nie jest.
     */
    @Test
    void setBrand_dodaje_experta_jesli_jeszcze_nie_Ma() {
        seller.setBrand(brand);
        assertEquals(brand, seller.getBrand());
    }

    /**
     * Testuje, czy metoda {@link Seller#showInformation()} zwraca czytelną i poprawną informację o sprzedawcy.
     */
    @Test
    void showInformation_zwraca_czytelna_Informacje() {
        String info = seller.showInformation();
        assertTrue(info.contains("Name: Ivan"));
        assertTrue(info.contains("Sales count: 0"));
    }
}
