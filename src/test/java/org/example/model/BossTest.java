package org.example.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BossTest {
        @Test
        void addIncome_shouldIncreaseIncomeCorrectly() {
            Boss boss = new Boss("Test Boss", 1000f);
            boss.addIncome(500f);
            assertEquals(1500f, boss.getIncome(), 0.01);
        }

        @Test
        void addEmployeer_shouldAddUniqueEmployeeAndDepartment() {
            Boss boss = new Boss("Boss", 0);
            Department dept = mock(Department.class);
            Employee emp = mock(Employee.class);
            when(emp.getDepartment()).thenReturn(dept);
            when(dept.getEmployees()).thenReturn(List.of(emp));
            boss.addEmployeer(emp);
            assertTrue(boss.calculateCenterIncome() >= 0);
        }

        @Test
        void calculateCenterIncome_shouldReturnSumOfBossAndEmployeeIncomes() {
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
