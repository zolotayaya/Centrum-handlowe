package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Boss;
import org.example.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Klasa {@code BossDB} odpowiada za operacje bazodanowe związane z obiektem {@link Boss}.
 * Implementuje wzorzec Singleton dla dyrektora centrum handlowego.
 */
public class BossDB {
    /** Jedyna instancja obiektu Boss (Singleton) */
    private static Boss instance;
    private List<Employee> allEmployees;
    private List<Department> allDepartment;
    /** Połączenie z bazą danych */
    private static Connection connection;

    /**
     * Konstruktor klasy {@code BossDB}.
     * Inicjalizuje połączenie z bazą danych oraz listy pracowników i działów.
     */
    public BossDB() {
        this.allEmployees = new ArrayList<>();
        this.allDepartment = new ArrayList<>();
        this.connection = Database.getConnection();
    }

    /**
     * Ładuje dane dyrektora z bazy danych. Jeśli brak danych – tworzy domyślnego dyrektora.
     *
     * @return instancja obiektu {@link Boss}
     * @throws SQLException jeśli wystąpi błąd przy komunikacji z bazą danych
     */
    public static Boss setBossFromDB() throws SQLException {
        if (instance == null) {
            try{
                PreparedStatement stmt = connection.prepareStatement("SELECT name, income FROM Boss WHERE id = 1");
                ResultSet rs = stmt.executeQuery(
                );

                if (rs.next()) {
                    String name = rs.getString("name");
                    float income = rs.getFloat("income");
                    instance = new Boss(name, income); // Tworzenie obiektu Boss z danych z bazy
                } else {
                    instance = new Boss("Default Boss", 0);// Domyślny boss jeśli brak danych

                }
            } catch (SQLException e) {
                e.printStackTrace();
                instance = new Boss("Default Boss", 0);// Również domyślny w przypadku błędu
            }
        }
        return instance;
    }
    /**
     * Zwraca instancję dyrektora.
     *
     * @return obiekt {@link Boss}
     */
    public static Boss getBoss() {
        return instance;
    }

    /**
     * Aktualizuje dochód dyrektora w bazie danych.
     *
     * @throws SQLException jeśli wystąpi błąd podczas aktualizacji
     */
    public  void updateIncomeInDatabase() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE boss SET income = ? WHERE id = 0");
        stmt.setFloat(1, instance.getIncome());
        stmt.executeUpdate();
    }
    /**
     * Oblicza całkowity dochód w zadanym przedziale czasowym
     * na podstawie danych z tabeli {@code Purchase_History}.
     *
     * @param startDate data początkowa
     * @param endDate   data końcowa
     * @return całkowity dochód wygenerowany w danym okresie
     */
    public float CalculateTotalIncomeFromHistory(LocalDate startDate, LocalDate endDate) {
        float totalIncome = 0f;

        String sql = "SELECT quantity, price FROM Purchase_History WHERE purchase_date BETWEEN ? AND ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Ustawienie zakresu dat do zapytania
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                // Dla każdego rekordu  mnoży ilość przez cenę i dodaje do sumy
                while (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    totalIncome += quantity * price;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching income from DB: " + e.getMessage());
        }

        return totalIncome;
    }

}