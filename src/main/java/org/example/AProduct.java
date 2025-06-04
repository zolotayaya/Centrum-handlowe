package org.example;

public abstract class AProduct {
    private String model;

    private float basePrice;
    public float calculateFinallPrice() {
        return 0;
    }
    public String getDetails(){
        return " ";
    }

    private int id;
    private String name;
    private String description;
    public abstract void displayInfo();


}

