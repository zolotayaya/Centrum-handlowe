package org.example.model;

import java.util.*;
public class Brand {
    private int id;
    private String name;
    private List<Seller> experts;
    private Department department;
    private List<Product> products;


    public Brand(String name, Department department) {
        this.name = name;
        this.department = department;
        this.products = new ArrayList<>();
        this.experts = new ArrayList<>();
    }

 public void setExpert(Seller expert) {
        if (!experts.contains(expert)) {
            experts.add(expert);
        }
    }


    public void addProduct(Product product) {
        products.add(product);
    }

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

    public List<Product> getProducts() {
        return products;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    public Department getDepartment() {
        return department;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Seller> getExperts() {
        System.out.println("Size" + experts.size());
        return experts;
    }
}
