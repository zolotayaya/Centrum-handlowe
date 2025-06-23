package org.example.model;

// Abstrakcyjna klasa reprezentująca pracownika
public abstract class Employee {
    private int id;
    private final String name;
    private final Department department;
    protected float income;
    private final float commision;
    private final int experience;

    /**
     * Konstruktor klasy Employee.
     *
     * @param id          unikalny identyfikator pracownika
     * @param name        imię pracownika
     * @param department  dział, do którego pracownik należy
     * @param income      dochód pracownika
     * @param commision   prowizja pracownika
     * @param experience  liczba lat doświadczenia
     */
    public Employee(int id, String name, Department department, float income, float commision, int experience) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.income = income;
        this.commision = commision;
        this.experience = experience;
    }

    /**
     * Abstrakcyjna metoda do wyświetlania informacji o pracowniku.
     * Musi zostać zaimplementowana w klasach dziedziczących.
     *
     * @return String zawierający informacje o pracowniku
     */
    public abstract String showInformation();

    /**
     * Ustawia dział pracownika na podstawie nazwy działu.
     * (Obecna implementacja tworzy nowy obiekt Department na podstawie nazwy)
     *
     * @param department nazwa działu
     * @return nowy obiekt Department o podanej nazwie
     */
    public Department setDepartment(String department) {
        return new Department(department);
    }

    // Gettery i setter

    /**
     * Zwraca unikalny identyfikator pracownika.
     *
     * @return id pracownika
     */
    public int getId() {
        return id;
    }

    /**
     * Zwraca imię pracownika.
     *
     * @return imię pracownika
     */
    public String getName() {
        return name;
    }

    /**
     * Zwraca dział, do którego przypisany jest pracownik.
     *
     * @return dział pracownika
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Zwraca dochód pracownika.
     *
     * @return dochód pracownika
     */
    public float getIncome() {
        return income;
    }

    /**
     * Zwraca prowizję pracownika.
     *
     * @return prowizja pracownika
     */
    public float getCommision() {
        return commision;
    }

    /**
     * Zwraca liczbę lat doświadczenia pracownika.
     *
     * @return doświadczenie w latach
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Ustawia dochód pracownika.
     *
     * @param income nowy dochód
     */
    public void setIncome(float income) {
        this.income = income;
    }
}
