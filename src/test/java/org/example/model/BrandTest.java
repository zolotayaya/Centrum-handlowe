package org.example.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Testy jednostkowe klasy {@link Brand}.
 */
class BrandTest {
    private Brand brand;
    private Department department;

    /**
     * Inicjalizuje przykładowe dane przed każdym testem.
     */
    @BeforeEach
    void setUp() {
        department = new Department("Electronics");
        brand = new Brand("Samsung", department);
    }

    /**
     * Testuje, czy konstruktor klasy {@link Brand} poprawnie inicjalizuje pola:
     * nazwę, dział oraz listy produktów i ekspertów (które powinny być puste).
     */
    @Test
    void constructor_powinny_inicjalizowac_pola_Correctly() {
        assertEquals("Samsung", brand.getName());
        assertEquals(department, brand.getDepartment());
        assertTrue(brand.getProducts().isEmpty());
        assertTrue(brand.getExperts().isEmpty());
    }

    /**
     * Testuje, czy metoda {@link Brand#addProduct(Product)} poprawnie dodaje produkt do listy.
     */
    @Test
    void addProduct_shouldAddProductToList() {
        Product product = new Product("Apple", 1000, 3, "Super", null, 1);
        brand.addProduct(product);
        assertEquals(1, brand.getProducts().size());
        assertEquals(product, brand.getProducts().get(0));
    }

    /**
     * Testuje, czy metoda {@link Brand#getDetails()} zwraca poprawny opis marki,
     * gdy przypisano eksperta (sprzedawcę).
     */
    @Test
    void getDetails_zwraca_poprawny_String_z_Eksperts() {
        Seller expert = new Seller(11, "John", null, 110, 10, 1, 4, 5);
        brand.setExpert(expert);
        String details = brand.getDetails();
        assertTrue(details.contains("Samsung"));
        assertTrue(details.contains("Experts: John"));
        assertTrue(details.contains("Products:"));
    }

    /**
     * Testuje, czy metoda {@link Brand#getDetails()} działa poprawnie,
     * gdy marka nie ma przypisanych ekspertów.
     */
    @Test
    void getDetails_zwraca_poprawny_String_bez_Ekspertow() {
        String details = brand.getDetails();
        assertTrue(details.contains("No experts assigned"));
        assertTrue(details.contains("Products:"));
    }

    /**
     * Testuje działanie metod {@link Brand#setId(int)} oraz {@link Brand#getId()}.
     */
    @Test
    void setAndGetId_dziala_poprawnie() {
        brand.setId(10);
        assertEquals(10, brand.getId());
    }
}
