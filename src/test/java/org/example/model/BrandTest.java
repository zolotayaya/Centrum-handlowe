package org.example.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
class BrandTest {
        private Brand brand;
        private Department department;

        @BeforeEach
        void setUp() {
            department = new Department("Electronics");
            brand = new Brand("Samsung", department);
        }

        @Test
        void constructor_powinny_inicjalizowac_pola_Correctly() {
            assertEquals("Samsung", brand.getName());
            assertEquals(department, brand.getDepartment());
            assertTrue(brand.getProducts().isEmpty());
            assertTrue(brand.getExperts().isEmpty());
        }


        @Test
        void addProduct_shouldAddProductToList() {
            Product product = new Product("Apple",1000,3,"Super",null,1);
            brand.addProduct(product);
            assertEquals(1, brand.getProducts().size());
            assertEquals(product, brand.getProducts().get(0));
        }

        @Test
        void getDetails_zwraca_poprawny_String_z_Eksperts() {
            Seller expert = new Seller(11,"John",null,110,10,1,4,5);
            brand.setExpert(expert);
            String details = brand.getDetails();
            assertTrue(details.contains("Samsung"));
            assertTrue(details.contains("Experts: John"));
            assertTrue(details.contains("Products:"));
        }

        @Test
        void getDetails_zwraca_poprawny_String_bez_Ekspertow() {
            String details = brand.getDetails();
            assertTrue(details.contains("No experts assigned"));
            assertTrue(details.contains("Products:"));
        }

        @Test
        void setAndGetId_dziala_poprawnie() {
            brand.setId(10);
            assertEquals(10, brand.getId());
        }
    }
