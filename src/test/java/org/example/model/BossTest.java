package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe klasy {@link Boss}.
 */
class BossTest {

    /**
     * Testuje, czy metoda {@link Boss#addIncome(float)} poprawnie zwiększa dochód szefa.
     * Początkowy dochód to 1000, dodajemy 500 — oczekiwany wynik: 1500.
     */
    @Test
    void addIncome_powinny_ZwiekszacInComeCorrectly() {
        Boss boss = new Boss("Test Boss", 1000f);
        boss.addIncome(500f);
        assertEquals(1500f, boss.getIncome(), 0.01);
    }

    /**
     * Testuje dodanie unikalnego pracownika typu {@link Seller} z przypisanym działem {@link Department} do szefa.
     * Sprawdza, czy po dodaniu pracownika dochód szefa jest poprawny (nieujemny).
     */
    @Test
    void addEmployeer_Unikalny_Employyer_i_Department() {
        Boss boss = new Boss("Boss", 0);
        Department dept = new Department("Apple");
        Seller emp = new Seller(10, "Nick", dept, 100, 12, 2, 5, 6);
        boss.addEmployeer(emp);

        assertTrue(boss.getIncome() >= 0); // podstawowe sprawdzenie dochodu
    }

    /**
     * Testuje, czy metoda {@link Boss#calculateCenterIncome()} poprawnie oblicza całkowity dochód,
     * który zawiera dochód szefa oraz dochody wszystkich dodanych pracowników.
     * Przykład: 1000 (szef) + 200 (sprzedawca) + 300 (menedżer) = 1500.
     */
    @Test
    void calculateCenterIncome_zwracaSume_OfBossAndEmployeeIncomes() {
        Boss boss = new Boss("Boss", 1000f);
        Department dept = new Department("Sales");
        Employee emp1 = new Seller(1, "John", dept, 200f, 12, 1, 4, 5);
        Employee emp2 = new Manager(10, "Nick", dept, 300f, 13, 4);
        boss.addEmployeer(emp1);
        boss.addEmployeer(emp2);

        float total = boss.calculateCenterIncome();
        assertEquals(1500f, total, 0.01);
    }
}
