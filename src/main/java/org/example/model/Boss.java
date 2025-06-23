package org.example.model;

import org.example.dao.BossDB;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * Klasa reprezentująca szefa w systemie.
 * Implementuje wzorzec Singleton, aby zapewnić tylko jedną instancję.
 */
public class Boss {
    /** Jedyna instancja klasy Boss (Singleton) */
    private static Boss instance;

    /** Imię szefa */
    private String name;

    /** Dochód szefa */
    private float income;

    /** Lista wszystkich pracowników podlegających szefowi */
    private List<Employee> allEmployees;

    /** Lista wszystkich działów zarządzanych przez szefa */
    private List<Department> allDepartment;

    /** Stały identyfikator szefa */
    private final int id = 1;

    /**
     * Konstruktor klasy Boss.
     *
     * @param name imię szefa
     * @param income początkowy dochód szefa
     */
    public Boss(String name, float income) {
        this.name = name;
        this.income = income;
        this.allEmployees = new ArrayList<>();
        this.allDepartment = new ArrayList<>();
    }

    /**
     * Dodaje dział do listy działów szefa, jeśli jeszcze go nie ma.
     * Przy dodaniu działu automatycznie dodaje pracowników tego działu.
     *
     * @param department dział do dodania
     */
    public void addDepartment(Department department) {
        if (!allDepartment.contains(department)) {
            allDepartment.add(department);
            department.getEmployees().forEach(this::addEmployeer);
        }
    }

    /**
     * Dodaje pracownika do listy wszystkich pracowników szefa,
     * a jeśli dział pracownika nie jest jeszcze dodany, to również go dodaje.
     *
     * @param employee pracownik do dodania
     */
    public void addEmployeer(Employee employee) {
        if (!allEmployees.contains(employee)) {
            allEmployees.add(employee);
            if (employee.getDepartment() != null && !allDepartment.contains(employee.getDepartment())) {
                addDepartment(employee.getDepartment());
            }
        }
    }

    /**
     * Dodaje podaną kwotę do dochodu szefa.
     *
     * @param amount kwota do dodania
     */
    public void addIncome(float amount) {
        this.income += amount;
    }

    /**
     * Zwraca aktualny dochód szefa.
     *
     * @return dochód szefa
     */
    public float getIncome() {
        return income;
    }

    /**
     * Oblicza całkowity dochód centrum, sumując dochód szefa oraz dochody wszystkich pracowników.
     *
     * @return całkowity dochód centrum
     */
    public float calculateCenterIncome() {
        float total = this.income;
        for (Employee i : allEmployees) {
            total += i.getIncome();
        }
        return total;
    }

    /**
     * Zwraca instancję szefa (Singleton).
     * Jeśli instancja nie istnieje, tworzy ją na podstawie danych pobranych z bazy danych.
     *
     * @return instancja szefa
     */
    public static Boss getInstance() {
        if (instance == null) {
            try {
                instance = BossDB.setBossFromDB();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    /**
     * Zwraca imię szefa.
     *
     * @return imię szefa
     */
    public String getName() {
        return name;
    }
}
