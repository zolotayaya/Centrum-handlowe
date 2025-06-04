package org.example;

public class Product extends AProduct{
    private int id;
    private String model;
    private float price;
    private int quantity;
    private String description;
    private Brand brand;

    public Product(String model, float price, int quantity,  String description, Brand brand) {
        this.model = model;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.brand=brand;
        if (brand!=null){
            brand.addProduct(this);
        }
    }

    public String getDetails() {
        return "Model: " + model + ", Price: " + price + ", Quantity: " + quantity + ", Description: " + description;
    }

    @Override
    public void displayInfo() {

    }

    public float calculateFinalPrice(int quantity_sold ){
        return price*quantity_sold;
    }
    public boolean updateQuantity(int quantity_sold) {
        if (quantity_sold <= 0) {
            System.out.println("The number of products sold must be positive");
            return false;
        }

        if (quantity_sold > this.quantity) {
            System.out.println("Not enough products in stock. Available: " + this.quantity);
            return false;
        }

        this.quantity -= quantity_sold;
        return true;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getName() {return model;}
    public Brand getBrand(){return brand;}
    public float getPrice(){return price;}
}
