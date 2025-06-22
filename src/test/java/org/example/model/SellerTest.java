package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

class SellerTest {
        private Department department;
        private Seller seller;
        private Product product;
        private Brand brand;

        @BeforeEach
        void setUp() {
            department = new Department("Sales");
            brand = new Brand("Samsung",department);
            product = new Product("Galaxy S24", 800.0f, 10, "Smartphone", brand, 1);
            seller = new Seller(1, "Ivan", department, 2000f, 10f, 0, 4.5f, 3);
        }

        @Test
        void constructor_inicjalizowany_poprawnie() {
            assertEquals("Ivan", seller.getName());
            assertEquals(2000f, seller.getIncome());
            assertEquals(10f, seller.getCommision());
            assertEquals(4.5, seller.getRating(), 0.01);
            assertEquals(department, seller.getDepartment());
            assertTrue(department.getEmployees().contains(seller));
        }

//

        @Test
        void checkPromotionCondition_zwraca_False_gdy_niewystarczajaco_sprzedarzow() {
            assertFalse(seller.checkPromotionCondition());
        }

    @Test
    void checkPromotionCondition_zwraca_True_gdy_wystarczajaco_sprzedarzow() {
        Seller seller = new Seller(1,"Antuan", department, 2000f, 10f, 6, 4.5f, 3);
        assertTrue(seller.checkPromotionCondition());
    }

        @Test
        void addRating_usrednia_poprawnie() {
            seller.addRating(5);
            assertEquals((4.5 + 5) / 2.0, seller.getRating(), 0.01);
        }

        @Test
        void canSellNow_limit_oparty_na_Ocenie() {
            for (int i = 0; i < 5; i++) {
                seller.recordSale();
            }
            assertFalse(seller.canSellNow());
        }

        @Test
        void canSellNow_pozwakla_na_sprzedarz_jesli_dolny_limit() {
            seller.recordSale();
            assertTrue(seller.canSellNow());
        }

        @Test
        void setBrand_dodaje_experta_jesli_jeszcze_nie_Ma() {
            seller.setBrand(brand);
            assertEquals(brand, seller.getBrand());
        }

        @Test
        void showInformation_zwraca_czytelna_Informacje() {
            String info = seller.showInformation();
            assertTrue(info.contains("Name: Ivan"));
            assertTrue(info.contains("Sales count: 0"));
        }
    }
