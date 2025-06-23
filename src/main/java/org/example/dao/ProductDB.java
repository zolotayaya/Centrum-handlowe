package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Brand;
import org.example.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa {@code ProductDB} odpowiada za operacje dostępu do danych produktów ({@link Product}) z bazy danych.
 */
public class ProductDB {

    /** Lista wszystkich produktów wczytanych z bazy danych. */
    private static List<Product> products;

    /** Połączenie z bazą danych. */
    private static Connection connection;

    /**
     * Konstruktor inicjalizujący połączenie z bazą danych oraz listę produktów.
     */
    public ProductDB() {
        products = new ArrayList<>();
        connection = Database.getConnection();
    }

    /**
     * Wczytuje wszystkie produkty z tabeli {@code Products} w bazie danych i przypisuje im odpowiednie marki.
     *
     * @param brandDB instancja klasy {@link BrandDB} do wyszukiwania powiązanych marek
     * @throws SQLException jeśli wystąpi błąd podczas odczytu z bazy danych
     */
    public void setProductsFromDB(BrandDB brandDB) throws SQLException {
        String sql = "SELECT * FROM Products";
        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                String brand = rs.getString("brand");

                Brand brand1 = getBrand(brand, brandDB);
                if (brand1 == null) {
                    System.out.println("Brand nie znaleziono: " + brand);
                    continue;
                }

                products.add(new Product(name, price, quantity, description, brand1, id));
            }
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas wczytywania produktów z bazy danych", e);
        }
    }

    /**
     * Pomocnicza metoda zwracająca obiekt {@link Brand} na podstawie jego nazwy.
     *
     * @param brandName nazwa marki
     * @param brandDB instancja klasy {@link BrandDB}
     * @return obiekt {@code Brand}, jeśli znaleziono; w przeciwnym razie {@code null}
     */
    public Brand getBrand(String brandName, BrandDB brandDB) {
        List<Brand> brands = brandDB.getBrands();
        if (brandName == null) return null;

        for (Brand brand : brands) {
            if (brandName.trim().equalsIgnoreCase(brand.getName().trim())) {
                return brand;
            }
        }
        return null;
    }

    /**
     * Aktualizuje ilość danego produktu w tabeli {@code Products} w bazie danych.
     *
     * @param product produkt, którego ilość ma zostać zaktualizowana
     * @param quantity nowa ilość produktu
     * @throws SQLException jeśli wystąpi błąd podczas aktualizacji
     */
    public void updateQuantityInDB(Product product, int quantity) throws SQLException {
        String sql = "UPDATE Products SET quantity = ? WHERE name = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, quantity);
        st.setString(2, product.getName());
        st.executeUpdate();
    }

    /**
     * Zwraca listę wszystkich wczytanych produktów.
     *
     * @return lista obiektów {@link Product}
     */
    public List<Product> getProducts() {
        return products;
    }
}
