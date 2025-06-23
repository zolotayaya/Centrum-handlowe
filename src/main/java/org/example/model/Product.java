package org.example.model;


/**
 * Klasa Product reprezentuje produkt w systemie sklepu.
 */
public class Product {
    private int id;
    private String model;
    private float price;
    private int quantity;
    private String description;
    private Brand brand;

    /**
     * Konstruktor klasy Product.
     * Tworzy nowy produkt i przypisuje go do marki (jeśli marka nie jest nullem).
     *
     * @param model       nazwa/model produktu
     * @param price       cena jednostkowa produktu
     * @param quantity    dostępna ilość produktu
     * @param description opis produktu
     * @param brand       marka produktu
     * @param id          unikalny identyfikator produktu
     */
    public Product(String model, float price, int quantity,  String description, Brand brand, int id) {
        this.model = model;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.brand = brand;
        this.id = id;
        if (brand != null) {
            brand.addProduct(this); // Dodanie produktu do listy produktów marki
        }
    }

    /**
     * Zwraca szczegóły produktu w formie tekstowej.
     *
     * @return ciąg znaków z podstawowymi informacjami o produkcie
     */
    public String getDetails() {
        return "Model: " + model + ", Price: " + price + ", Quantity: " + quantity + ", Description: " + description;
    }

    /**
     * Oblicza łączną cenę na podstawie sprzedanej ilości.
     *
     * @param quantity_sold liczba sprzedanych sztuk
     * @return wartość całkowita sprzedaży
     */
    public float calculateFinalPrice(int quantity_sold) {
        return price * quantity_sold;
    }

    /**
     * Aktualizuje stan magazynowy produktu po sprzedaży.
     * Sprawdza poprawność ilości oraz dostępność.
     *
     * @param quantity_sold liczba sprzedanych sztuk
     * @return true jeśli aktualizacja się powiodła, false w przeciwnym razie
     */
    public boolean updateQuantity(int quantity_sold) {
        if (quantity_sold <= 0) {
            System.out.println("The number of products sold must be positive");
            return false;
        }

        if (quantity_sold > this.quantity) {
            System.out.println("Not enough products in stock. Available: " + this.quantity);
            return false;
        }
        this.quantity -= quantity_sold; // Zmniejszenie ilości produktu po sprzedaży
        System.out.println("Sold " + quantity_sold + ". Available: " + this.quantity);
        return true;
    }

    /**
     * Zwraca ilość dostępnych sztuk produktu.
     *
     * @return ilość produktu na stanie magazynowym
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Zwraca markę produktu.
     *
     * @return obiekt Brand reprezentujący markę produktu
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * Zwraca nazwę/model produktu.
     *
     * @return nazwa/model produktu
     */
    public String getName() {
        return model;
    }

    /**
     * Zwraca cenę jednostkową produktu.
     *
     * @return cena produktu
     */
    public float getPrice() {
        return price;
    }

    /**
     * Zwraca unikalny identyfikator produktu.
     *
     * @return id produktu
     */
    public int getId() {
        return id;
    }

    /**
     * Zwraca opis produktu.
     *
     * @return opis produktu
     */
    public String getDescription() {
        return description;
    }
}
