package org.example.model;

import org.example.IPromotable;
import org.example.dao.ManagerDB;
import org.example.dao.SellerDB;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Seller reprezentuje sprzedawcę w systemie.
 * Dziedziczy po klasie Employee i implementuje interfejs IPromotable,
 * co umożliwia awans na managera.
 */
public class Seller extends Employee implements IPromotable {
    private int salesCount;                      // Liczba zrealizowanych sprzedaży
    private Brand brand;                         // Marka, do której przypisany jest sprzedawca (jako ekspert)
    private double rating;                       // Ocena sprzedawcy (np. na podstawie opinii)
    private Manager promotedTo;                  // Manager, na którego sprzedawca został awansowany (jeśli awansowany)
    private List<LocalDateTime> saleTimestamps = new ArrayList<>(); // Lista czasów dokonanych sprzedaży (do limitów godzinowych)

    /**
     * Konstruktor tworzący nowego sprzedawcę.
     */
    public Seller(int id, String name, Department department, float income, float commision,
                  int salesCount, float rating, int experience) {
        super(id, name, department, income, commision, experience);

        this.salesCount = salesCount;
        this.rating = rating;
        if (department != null) {
            department.addEmployee(this);
        }
    }

    /**
     * Realizuje sprzedaż produktu przez sprzedawcę.
     * Sprawdza, czy sprzedawca nie został awansowany (wtedy nie może sprzedawać).
     * Aktualizuje stan produktu, liczbę sprzedaży, prowizję sprzedawcy, managera i szefa.
     * Sprawdza warunki do awansu i jeśli są spełnione, wykonuje promocję.
     *
     * @param product  sprzedawany produkt
     * @param quantity ilość sprzedanego produktu
     */
    public void saleProduct(Product product, int quantity) {
        if (promotedTo != null) {
            System.out.println("This seller has already been promoted and cannot sell anymore.");
            return;
        }
        float totalPrice = product.getPrice() * quantity;
        System.out.println("Product name: " + product.getName());
        product.updateQuantity(quantity);      // Aktualizacja ilości produktu w magazynie
        salesCount += 1;                       // Zwiększenie liczby sprzedaży
        income += totalPrice * (getCommision() / 100f);  // Dodanie prowizji sprzedawcy

        Department department = getDepartment();
        if (department != null) {
            Manager manager = department.getManager();
            if (manager != null) {
                // Dodanie prowizji managerowi działu
                manager.addIncome(totalPrice * (manager.getCommision() / 100f));
            }
        }

        // Dodanie prowizji szefowi firmy (stała 1%)
        Boss boss = Boss.getInstance();
        boss.addIncome(totalPrice * 0.01f);

        // Sprawdzenie warunków awansu i ewentualne wykonanie promocji
        if (checkPromotionCondition()) {
            executePromotion();
        }
    }

    /**
     * Sprawdza, czy sprzedawca spełnia warunki do awansu na managera.
     * Warunki: co najmniej 5 sprzedaży, dział przypisany i co najmniej 2 sprzedawców w dziale.
     *
     * @return true, jeśli warunki są spełnione, false w przeciwnym wypadku
     */
    @Override
    public boolean checkPromotionCondition() {
        Department department = getDepartment();
        return salesCount >= 5 && department != null && department.getSellers().size() > 1;
    }

    /**
     * Wykonuje awans sprzedawcy na managera.
     * Usuwa sprzedawcę z działu i bazy danych,
     * usuwa starego managera, tworzy nowego managera,
     * aktualizuje bazę danych i strukturę działu.
     *
     * @return true jeśli awans się powiódł, false jeśli dział nie istnieje
     */
    @Override
    public boolean executePromotion() {
        Department department = getDepartment();
        if (department == null) return false;

        // Usunięcie przypisania jako ekspert marki, jeśli dotyczy
        if (brand != null) {
            brand.setExpert(null);
            brand = null;
        }

        // Usunięcie starego managera z działu i bazy danych
        Manager oldManager = department.getManager();
        if (oldManager != null) {
            department.fireManager();
            try {
                ManagerDB.deleteManagerById(oldManager.getId());
                System.out.println("Old manager removed from DB.");
            } catch (SQLException e) {
                System.err.println("Failed to delete old manager: " + e.getMessage());
            }
        }

        // Utworzenie nowego managera na bazie sprzedawcy
        Manager newManager = new Manager(getId(), getName(), department, getIncome(), getCommision(), getExperience());

        // Aktualizacja działu: usunięcie sprzedawcy, dodanie managera
        department.removeEmployee(this);
        department.addEmployee(newManager);
        this.promotedTo = newManager;

        System.out.println(getName() + " was promoted to manager!");

        // Aktualizacja bazy danych: dodanie managera i usunięcie sprzedawcy
        try {
            ManagerDB.insertManager(newManager);
            SellerDB.deleteSellerById(getId());
            System.out.println("Promotion persisted to DB.");
        } catch (SQLException e) {
            System.err.println("Failed to update database: " + e.getMessage());
        }
        return true;
    }

    /**
     * Zwraca podstawowe informacje o sprzedawcy w formie tekstowej.
     */
    @Override
    public String showInformation() {
        return "Name: " + getName() + ", Department: " + getDepartment() +
                ", Income: " + getIncome() + ", Commision: " + getCommision() +
                ", Sales count: " + getsalesCount();
    }

    /**
     * Przypisuje sprzedawcy markę, do której jest ekspertem.
     *
     * @param brand marka do przypisania
     */
    public void setBrand(Brand brand) {
        if (this.brand != null) {
            System.out.println("This seller is already assigned to the brand: " + this.brand.getName());
        }
        this.brand = brand;
    }

    /**
     * Sprawdza, czy sprzedawca może aktualnie dokonać sprzedaży,
     * na podstawie limitu sprzedaży w ciągu ostatniej godziny (limit zależny od oceny).
     *
     * @return true jeśli sprzedawca może sprzedawać, false jeśli limit został osiągnięty
     */
    public boolean canSellNow() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        long recentSales = saleTimestamps.stream()
                .filter(t -> t.isAfter(oneHourAgo))
                .count();
        int limit = (int) Math.max(1, Math.round(getRating())); // Zaokrąglenie ratingu do najbliższej liczby całkowitej
        return recentSales < limit;
    }

    /**
     * Rejestruje czas dokonanej sprzedaży.
     */
    public void recordSale() {
        saleTimestamps.add(LocalDateTime.now());
    }

    /**
     * Dodaje nową ocenę sprzedawcy i uśrednia ją z dotychczasową.
     *
     * @param newRating nowa ocena do uwzględnienia
     */
    public void addRating(int newRating) {
        this.rating = (this.rating + newRating) / 2.0;
    }

    // Gettery
    public Brand getBrand() {
        return brand;
    }

    public int getsalesCount() {
        return salesCount;
    }

    @Override
    public float getIncome() {
        return income;
    }

    public double getRating() {
        return rating;
    }
}
