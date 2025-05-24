package org.example;

import java.util.*;

public class Product {
    private String model;
    private int id;
    private float price;
    private int quantity;
    private String description;
    public ArrayList<String> product_info = new ArrayList<>();
    public void updateQuantity(int changes){

    }
    public void setName(String newName){
        model = newName;
    }
    public String getName(){
        return model;
    }

    public void setId(int ids){
        this.id = ids;
    }
    public int getId(){
        return id;
    }

    public void setPrice(float price){
        this.price = price;
    }
    public float getPrice(){
        return price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public int getQuantity(){
        return quantity;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
}
