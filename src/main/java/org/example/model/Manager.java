package org.example.model;

import java.util.*;
/**
 *      Klasa Manager reprezentuje menedżera w firmie, który jest pracownikiem (dziedziczy po klasie Employee).
 *
 *
 */
public class Manager extends Employee {
    private List<Seller> managedSellers; // Lista sprzedawców zarządzanych przez tego menedżera

    /**
     * Konstruktor klasy Manager.
     * Przypisuje menedżera do podanego działu, jeśli dział nie ma jeszcze menedżera.
     * Inicjalizuje listę sprzedawców zarządzanych przez menedżera.
     *
     * @param id          unikalny identyfikator menedżera
     * @param name        imię menedżera
     * @param department  dział, którym menedżer będzie zarządzał
     * @param income      dochód menedżera
     * @param commision   prowizja menedżera
     * @param experience  doświadczenie menedżera w latach
     */
    public Manager(int id, String name, Department department, float income, float commision, int experience) {
        super(id, name, department, income, commision, experience);
        if (department.getManager() != null) {
            System.out.println("This department already has a manager: " + department.getManager().getName());
        } else {
            this.managedSellers = new ArrayList<>();
            department.setManager(this);
        }
    }

    /**
     * Zwraca listę sprzedawców zarządzanych przez menedżera.
     *
     * @return lista sprzedawców (Seller)
     */
    public List<Seller> getSeller() {
        return managedSellers;
    }

    /**
     * Dodaje sprzedawcę do listy zarządzanych przez menedżera.
     *
     * @param seller sprzedawca do dodania
     */
    public void addSeller(Seller seller) {
        managedSellers.add(seller);
    }

    /**
     * Usuwa sprzedawcę z listy zarządzanych na podstawie jego identyfikatora.
     *
     * @param id identyfikator sprzedawcy do usunięcia
     */
    public void removeSeller(int id) {
        managedSellers.removeIf(seller -> seller.getId() == id);
    }

    /**
     * Zwraca sformatowane informacje o menedżerze.
     *
     * @return ciąg znaków zawierający podstawowe dane menedżera
     */
    @Override
    public String showInformation() {
        return "Name: " + getName() + ", Department: " + getDepartment() + ", Income: " + getIncome() + ", Commision: " + getCommision();
    }

    /**
     * Zwraca reprezentację tekstową obiektu menedżera.
     *
     * @return ciąg znaków opisujący menedżera (imię i ID)
     */
    @Override
    public String toString() {
        return "Manager { name='" + getName() + "', id=" + getId() + "}";
    }

    /**
     * Dodaje określoną kwotę do dochodu menedżera.
     *
     * @param amount kwota do dodania
     */
    public void addIncome(float amount) {
        this.income += amount;
    }
}
