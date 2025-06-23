package org.example.model;

import java.util.*;

/**
 * Klasa reprezentująca dział w centrum handlowym
 */

public class Department {
    private String name;
    private Manager manager;
    private List<Brand> brands;
    private List<Employee> employees;

    /**
     * Konstruktor tworzący dział o podanej nazwie
     */

    public Department(String name){
        this.name = name;
        this.brands = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Zwraca listę produktów powiązanych z podaną marką w tym dziale.
     * Obecnie metoda zwraca pustą listę.
     *
     * @param brand marka, dla której chcemy pobrać produkty
     * @return lista produktów powiązanych z marką
     */
    public List<Product> getProductsByBrand(Brand brand){
        return new ArrayList<>();
    }

    /**
     * Zwraca obecnego kierownika działu.
     *
     * @return kierownik działu lub null jeśli nie został przypisany
     */
    public Manager getManager(){
        return manager;
    }

    /**
     * Zwraca listę sprzedawców zatrudnionych w dziale.
     *
     * @return lista pracowników typu Seller
     */
    public List<Seller> getSellers() {
        List<Seller> sellers = new ArrayList<>();
        for (Employee e : employees) {
            if (e instanceof Seller) {
                sellers.add((Seller) e);
            }
        }
        return sellers;
    }

    /**
     * Ustawia kierownika działu, jeśli jeszcze nie jest przypisany.
     * Jeśli kierownik jest już przypisany, metoda nic nie robi.
     * Dodaje kierownika do listy pracowników, jeśli jeszcze tam nie figuruje.
     *
     * @param manager nowy kierownik działu
     */
    public void setManager(Manager manager){
        if (this.manager != null) {
            return; // Kierownik już jest przypisany, kończy działanie
        }
        this.manager = manager;
        if (!employees.contains(manager)) {
            employees.add(manager);
        }
        System.out.print("New manager is set");
    }

    /**
     * Dodaje pracownika do działu.
     * Jeśli dodawany pracownik jest kierownikiem, a dział ma już kierownika,
     * dodanie zostaje zablokowane, a metoda zwraca false.
     *
     * @param employee pracownik do dodania
     * @return true jeśli dodano pracownika, false jeśli dodanie zostało zablokowane
     */
    public boolean addEmployee(Employee employee) {
        if (employee instanceof Manager) {
            if (getManager() != null) {
                System.out.println("There is already a manager in the department!: " + employee.getName());
                return false;
            }
            setManager((Manager) employee);
        }
        employees.add(employee);
        return true;
    }

    /**
     * Zwalnia (usuwa) obecnego kierownika działu z listy pracowników i usuwa przypisanie.
     */
    public void fireManager() {
        Manager manager = getManager();
        if (manager != null) {
            employees.remove(manager);
            System.out.println("Manager " + manager.getName() + " was fired.");
            this.manager = null;
        }
    }

    /**
     * Usuwa podanego pracownika z listy pracowników działu.
     *
     * @param e pracownik do usunięcia
     */
    public void removeEmployee(Employee e) {
        employees.remove(e);
    }

    /**
     * Zwraca nazwę działu.
     *
     * @return nazwa działu
     */
    public String getName() {
        return name;
    }

    /**
     * Zwraca listę wszystkich pracowników zatrudnionych w dziale.
     *
     * @return lista pracowników
     */
    public List<Employee> getEmployees() {
        return employees;
    }
}
