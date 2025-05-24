package org.example;
import java.util.*;
public class Brand {
    private int id;
    private String name;
    private Seller expert;
    private Department department;
    private List<Product> products;

    //public Brand() {}

    public Brand( String name,Department department) {
        this.name = name;
        this.department=department;
        this.products = new ArrayList<>();
        //setExpert(expert);
    }
    public void setExpert(Seller expert) {
        // if (expert == null) {
        //   System.out.println("An expert cannot be null");
        //   return;
        //  }

        if (this.expert != null) {
            System.out.println("This brand already has an expert: " + this.expert.getName());
            return;
        }
        if (expert.getBrand() != null) {
            System.out.println("This seller is already assigned to another brand: " + expert.getBrand().getName());
            return;
        }
        if(!expert.getDepartment().equals(this.department)){
            System.out.println("This expert is from another department");
            return;
        }

        this.expert = expert;
        expert.setBrand(this);
        System.out.println("Expert is set");
    }

    public void addProduct(Product product) {
        products.add(product);
    }
    public String getDetails() {
        String result = "Brand ID: " + id +
                ", Name: " + name +
                ", Expert: " + expert.getName() +
                "\nProducts:\n";

        for (Product product : products) {
            result += " - " + product.getDetails() + "\n";
        }
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

    public void setId(int id) {
        this.id = id;
    }

    public Seller getExpert() {
        return expert;
    }
}
