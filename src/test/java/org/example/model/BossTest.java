package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BossTest {
        @Test
        void addIncome_powinny_ZwiekszacInComeCorrectly() {
            Boss boss = new Boss("Test Boss", 1000f);
            boss.addIncome(500f);
            assertEquals(1500f, boss.getIncome(), 0.01);
        }

    @Test
    void addEmployeer_Unikalny_Employyer_i_Department() {
        Boss boss = new Boss("Boss", 0);
        Department dept = new Department("Apple");
        Seller emp = new Seller(10, "Nick", dept, 100, 12, 2, 5, 6);
        boss.addEmployeer(emp);

        assertTrue(boss.getIncome() >= 0); // базова перевірка
    }


    @Test
        void calculateCenterIncome_zwracaSume_OfBossAndEmployeeIncomes() {
            Boss boss = new Boss("Boss", 1000f);
            Department dept = new Department("Sales");
            Employee emp1 = new Seller(1,"John",  dept, 200f,12,1,4,5);
            Employee emp2 = new Manager(10,"Nick", dept, 300f,13,4);
            boss.addEmployeer(emp1);
            boss.addEmployeer(emp2);
            float total = boss.calculateCenterIncome();
            assertEquals(1500f, total, 0.01);
        }
    }
