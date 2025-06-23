package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Klasa SellerDB odpowiada za operacje bazodanowe związane z obiektami typu Seller.
 * Implementuje wzorzec Singleton, aby zapewnić pojedynczą instancję.
 */
public class SellerDB {
    /** Jedyna instancja klasy SellerDB (Singleton) */
    private static SellerDB instance;

    /** Lista sprzedawców pobranych z bazy danych */
    private static List<Seller> sellers;

    /** Połączenie z bazą danych */
    private static Connection connection;

    /** Obiekt Random do losowania wartości */
    public Random random;

    /**
     * Konstruktor inicjujący listę sprzedawców, połączenie z bazą i obiekt Random.
     */
    public SellerDB() {
        sellers = new ArrayList<Seller>();
        this.connection = Database.getConnection();
        this.random = new Random();
    }

    /**
     * Zwraca instancję klasy SellerDB (wzorzec Singleton).
     *
     * @return instancja SellerDB
     */
    public static synchronized SellerDB getInstance() {
        if (instance == null) {
            instance = new SellerDB();
        }
        return instance;
    }

    /**
     * Wczytuje sprzedawców z bazy danych i zapisuje ich do listy sellers.
     *
     * @throws SQLException gdy wystąpi błąd podczas zapytania do bazy danych
     */
    public void setSellersFromDB() throws SQLException {
        String sql = "SELECT * FROM Sellers";
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String depname = rs.getString("department");
            float income = rs.getFloat("salary");
            float commision = rs.getFloat("commission");
            int salesCount = rs.getInt("salescount");
            float rating = rs.getFloat("rating");
            int experience = rs.getInt("experience_years");
            // Tworzenie obiektu Seller i dodanie do listy
            sellers.add(new Seller(id, name, getDep(depname), income, commision, salesCount, rating, experience));
        }
    }

    /**
     * Zwraca obiekt Department na podstawie nazwy departamentu.
     *
     * @param depName nazwa departamentu
     * @return obiekt Department lub null, jeśli nie znaleziono
     */
    public Department getDep(String depName) {
        List<Department> department = DepartmentDB.getDepartments();
        if (depName == null) return null;
        for (Department dep : department) {
            if (depName.trim().equalsIgnoreCase(dep.getName().trim())) {
                return dep;
            }
        }
        return null;
    }

    /**
     * Ustawia losowe doświadczenie (w latach) dla każdego sprzedawcy w podanym zakresie.
     *
     * @param min minimalna wartość doświadczenia
     * @param max maksymalna wartość doświadczenia
     * @throws SQLException gdy wystąpi błąd podczas aktualizacji danych w bazie
     */
    public void setExperience(int min, int max) throws SQLException {
        for (Seller seller : sellers) {
            String sql = "UPDATE Seller SET experience_years = ? WHERE name = ?";
            int experience = min + random.nextInt(max - min);
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, experience);
            st.setString(2, seller.getName());
            st.executeUpdate();
        }
    }

    /**
     * Aktualizuje pensję oraz liczbę sprzedaży danego sprzedawcy w bazie danych.
     *
     * @param seller obiekt Seller do zaktualizowania
     * @throws SQLException gdy wystąpi błąd podczas aktualizacji danych w bazie
     */
    public static void updateSellerStats(Seller seller) throws SQLException {
        String sql = "UPDATE Sellers SET salary = ?, salescount = ? WHERE id = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setFloat(1, seller.getIncome());
        st.setInt(2, seller.getsalesCount());
        st.setInt(3, seller.getId());
        st.executeUpdate();
    }

    /**
     * Dodaje nowego sprzedawcę do lokalnej listy sprzedawców.
     *
     * @param seller obiekt Seller do dodania
     * @throws SQLException (nigdy nie rzucany, ale można zostawić zgodnie z sygnaturą)
     */
    public void updateSeller(Seller seller) throws SQLException {
        sellers.add(seller);
    }

    /**
     * Zwraca listę wszystkich sprzedawców.
     *
     * @return lista sprzedawców
     */
    public List<Seller> getSellers() {
        return sellers;
    }

    /**
     * Usuwa sprzedawcę z bazy danych oraz z lokalnej listy na podstawie ID.
     *
     * @param id identyfikator sprzedawcy do usunięcia
     * @throws SQLException gdy wystąpi błąd podczas usuwania z bazy danych
     */
    public static void deleteSellerById(int id) throws SQLException {
        String sql = "DELETE FROM Sellers WHERE id = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, id);
        st.executeUpdate();

        sellers.removeIf(s -> s.getId() == id);
    }

    /**
     * Aktualizuje ocenę (rating) danego sprzedawcy w bazie danych.
     *
     * @param seller obiekt Seller z nową oceną
     * @throws SQLException gdy wystąpi błąd podczas aktualizacji danych w bazie
     */
    public static void updateSellerRating(Seller seller) throws SQLException {
        String sql = "UPDATE sellers SET rating = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDouble(1, seller.getRating());
        stmt.setInt(2, seller.getId());
        stmt.executeUpdate();
        stmt.close();
    }
}
