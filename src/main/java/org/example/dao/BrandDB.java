package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Brand;
import org.example.model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Klasa {@code BrandDB} odpowiada za dostęp do danych marek ({@link Brand}) z bazy danych.
 * Obsługuje ich wczytywanie, wyszukiwanie oraz przypisywanie sprzedawców jako ekspertów.
 */
public class BrandDB {
    /** Lista wszystkich marek wczytanych z bazy danych */
    private static List<Brand> brands;
    /** Połączenie z bazą danych */
    private static Connection connection;
    /**
     * Konstruktor inicjalizuje połączenie z bazą danych oraz pustą listę marek.
     */
    public BrandDB() {
        brands = new ArrayList<Brand>();
        this.connection = Database.getConnection();
    }
    /**
     * Wczytuje wszystkie marki z tabeli {@code Brands} w bazie danych
     * i przypisuje je do listy {@code brands}, wraz z przypisaniem działu.
     *
     * @throws SQLException jeśli wystąpi błąd przy odczycie danych
     */
    public void setBrandFromDB() throws SQLException {
        String sql = "SELECT * FROM Brands";
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            String depname = rs.getString("department");
            brands.add(new Brand(name, getDep(depname)));// tworzenie obiektu Brand z przypisanym działem
        }
    }
    /**
     * Pomocnicza metoda zwracająca obiekt {@link Department} na podstawie jego nazwy.
     *
     * @param depName nazwa działu
     * @return obiekt {@code Department}, lub {@code null} jeśli nie znaleziono
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
     * Zwraca markę na podstawie jej nazwy.
     *
     * @param brandName nazwa marki
     * @return obiekt {@code Brand}, lub {@code null} jeśli nie znaleziono
     */
    public Brand getBrand(String brandName) {
        if (brandName == null) return null;
        for (Brand brand : brands) {
            if (brandName.trim().equalsIgnoreCase(brand.getName().trim())) {
                return brand;
            }
        }
        return null;
    }

    /**
     * Przypisuje maksymalnie dwóch sprzedawców jako ekspertów do każdej marki,
     * na podstawie zgodności działu sprzedawcy z działem marki.
     *
     * @param sellerList lista dostępnych sprzedawców
     */
    public void assignExpertsToBrands(List<Seller> sellerList) {
        List<Seller> assignedSellers = new ArrayList<>();

        for (Brand brand : brands) {
            int expertsAdded = 0;

            for (Seller seller : sellerList) {
                // Sprawdza, czy sprzedawca nie jest jeszcze przypisany oraz czy jego dział odpowiada działowi marki
                if (!assignedSellers.contains(seller) &&
                        seller.getDepartment() != null &&
                        brand.getDepartment() != null &&
                        seller.getDepartment().getName().equals(brand.getDepartment().getName())) {

                    brand.setExpert(seller);// przypisanie eksperta do marki
                    assignedSellers.add(seller);// dodanie do listy przypisanych

                    System.out.println(" Assigned " + seller.getName() + " to brand " + brand.getName());
                    expertsAdded++;
                    if (expertsAdded == 2) break;
                }
            }

            if (expertsAdded == 0) {
                System.out.println("No expert sellers found for brand: " + brand.getName());
            }
        }
    }

    /**
     * Zwraca listę wszystkich marek wczytanych z bazy danych.
     *
     * @return lista marek
     */
    public  List<Brand> getBrands() {
        return brands;
    }// Zwraca listę wszystkich marek
}
