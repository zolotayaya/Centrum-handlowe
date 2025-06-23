package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Klasa {@code DepartmentDB} odpowiada za operacje na tabeli {@code Departments} w bazie danych.
 * Obsługuje wczytywanie danych działów i udostępnia je aplikacji.
 */
public class DepartmentDB {
    /** Lista przechowująca wszystkie działy pobrane z bazy danych. */
    private static List<Department> department;
    /** Połączenie z bazą danych. */
    private static Connection connection;
    /**
     * Konstruktor inicjalizuje połączenie z bazą danych i listę działów.
     */
    public DepartmentDB() {
        department = new ArrayList<>();
        this.connection = Database.getConnection();
    }
    /**
     * Wczytuje wszystkie działy z tabeli {@code Departments} w bazie danych
     * i zapisuje je do listy {@code department}.
     *
     * @throws SQLException jeśli wystąpi błąd podczas komunikacji z bazą danych
     */
    public void setDepartmentsFromDB() throws SQLException {
        String sql = "SELECT * FROM Departments";
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            department.add(new Department(rs.getString("name"))); //tworzenie obiektów Department
        }
    }
    /**
     * Zwraca listę wszystkich działów pobranych z bazy danych.
     *
     * @return lista działów
     */
    public static List<Department> getDepartments() {
        return department;
    }
}
