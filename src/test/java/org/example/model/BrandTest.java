package org.example.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

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
        void constructor_shouldInitializeFieldsCorrectly() {
            assertEquals("Samsung", brand.getName());
            assertEquals(department, brand.getDepartment());
            assertTrue(brand.getProducts().isEmpty());
            assertTrue(brand.getExperts().isEmpty());
        }

        @Test
        void setExpert_shouldAddExpertOnce() {
            Seller seller = mock(Seller.class);
            brand.setExpert(seller);
            brand.setExpert(seller); // повторно

            List<Seller> experts = brand.getExperts();
            assertEquals(1, experts.size());
            assertTrue(experts.contains(seller));
        }

        @Test
        void addProduct_shouldAddProductToList() {
            Product product = mock(Product.class);
            brand.addProduct(product);
            assertEquals(1, brand.getProducts().size());
            assertEquals(product, brand.getProducts().get(0));
        }

        @Test
        void getDetails_shouldReturnCorrectStringWithExperts() {
            Seller expert = mock(Seller.class);
            when(expert.getName()).thenReturn("John");

            brand.setExpert(expert);

            String details = brand.getDetails();
            assertTrue(details.contains("Samsung"));
            assertTrue(details.contains("Experts: John"));
            assertTrue(details.contains("Products:"));
        }

        @Test
        void getDetails_shouldReturnCorrectStringWithoutExperts() {
            String details = brand.getDetails();
            assertTrue(details.contains("No experts assigned"));
            assertTrue(details.contains("Products:"));
        }

        @Test
        void setAndGetId_shouldWorkCorrectly() {
            brand.setId(10);
            assertEquals(10, brand.getId());
        }
    }
