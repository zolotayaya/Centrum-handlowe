package org.example.model;

import java.util.*;

/**
 * Klasa reprezentująca markę przypisaną do konkretnego działu.
 */
public class Brand {
    private int id;
    private String name;
    private List<Seller> experts;
    private Department department;
    private List<Product> products;

    /**
     * Konstruktor klasy Brand.
     *
     * @param name       nazwa marki
     * @param department dział, do którego marka jest przypisana
     */
    public Brand(String name, Department department) {
        this.name = name;
        this.department = department;
        this.products = new ArrayList<>();
        this.experts = new ArrayList<>();
    }

    /**
     * Dodaje eksperta do listy ekspertów marki, jeśli nie został wcześniej dodany.
     *
     * @param expert ekspert do dodania
     */
    public void setExpert(Seller expert) {
        if (!experts.contains(expert)) {
            experts.add(expert);
        }
    }

    /**
     * Dodaje produkt do listy produktów marki.
     *
     * @param product produkt do dodania
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Zwraca szczegóły marki w formie tekstowej, w tym ID, nazwę,
     * ekspertów oraz nagłówek dla listy produktów.
     *
     * @return tekstowy opis marki
     */
    public String getDetails() {
        String result = "Brand ID: " + id + ", Name: " + name;

        if (!experts.isEmpty()) {
            result += ", Experts: ";
            for (Seller expert : experts) {
                result += expert.getName() + " ";
            }
        } else {
            result += ", No experts assigned.";
        }
        result += "\nProducts:\n";

        return result;
    }

    /**
     * Zwraca listę produktów przypisanych do marki.
     *
     * @return lista produktów
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Zwraca nazwę marki.
     *
     * @return nazwa marki
     */
    public String getName() {
        return name;
    }

    /**
     * Zwraca identyfikator marki.
     *
     * @return ID marki
     */
    public int getId() {
        return id;
    }

    /**
     * Zwraca dział, do którego marka jest przypisana.
     *
     * @return dział marki
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Ustawia identyfikator marki.
     *
     * @param id identyfikator do ustawienia
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zwraca listę ekspertów przypisanych do marki.
     * Dodatkowo wypisuje na konsolę liczbę ekspertów.
     *
     * @return lista ekspertów
     */
    public List<Seller> getExperts() {
        System.out.println("Size: " + experts.size());
        return experts;
    }
}
