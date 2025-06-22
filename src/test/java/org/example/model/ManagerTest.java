package org.example.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ManagerTest {
        private Department department;
        private Manager manager;

        @BeforeEach
        void setUp() {
            department = new Department("Sales");
            manager = new Manager(1, "Olga", department, 5000f, 10f, 4);
        }

        @Test
        void constructor_przypisuje_managera_do_Department_ifNoneExists() {
            assertEquals(manager, department.getManager());
        }

        @Test
        void constructor_ostrzega_ifManagerAlreadyExists() {
            Manager second = new Manager(2, "Ivan", department, 4000f, 8f, 3);
            assertNotEquals(second, department.getManager());
        }

        @Test
        void addSeller_powinny_dodawac_ToManagedList() {
            Seller seller = new Seller(2, "Dmytro", department, 2500f, 5f, 10, 4.0f, 2);
            manager.addSeller(seller);

            List<Seller> sellers = manager.getSeller();
            assertEquals(1, sellers.size());
            assertEquals(seller, sellers.get(0));
        }

        @Test
        void removeSeller_usuwa_poprawnie_przez_Id() {
            Seller seller1 = new Seller(2, "Dmytro", department, 2500f, 5f, 10, 4.0f, 2);
            Seller seller2 = new Seller(3, "Ira", department, 2700f, 6f, 8, 3.8f, 3);

            manager.addSeller(seller1);
            manager.addSeller(seller2);
            manager.removeSeller(2);

            List<Seller> sellers = manager.getSeller();
            assertEquals(1, sellers.size());
            assertEquals(seller2, sellers.get(0));
        }

        @Test
        void showInformation_zwraca_czytelna_informacje() {
            String info = manager.showInformation();
            assertTrue(info.contains("Olga"));
            assertTrue(info.contains("Income: 5000.0"));
            assertTrue(info.contains("Commision: 10.0"));
        }

        @Test
        void toString_formatuje_Poprawnie() {
            String str = manager.toString();
            assertEquals("Manager { name='Olga', id=1}", str);
        }

        @Test
        void addIncome_zwieksza_Managers_Income_Correctly() {
            manager.addIncome(1000f);
            assertEquals(6000f, manager.getIncome());
        }
    }
