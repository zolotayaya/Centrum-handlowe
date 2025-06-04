package org.example;
import java.util.*;
public class Brand {
    private int id;
    private String name;
    private List<Seller> experts;
    private Department department;
    private List<Product> products;

    //public Brand() {}

    public Brand(String name, Department department) {
        this.name = name;
        this.department = department;
        this.products = new ArrayList<>();
        this.experts = new ArrayList<>();
    }

    public void setExpert(Seller expert) {
        this.experts.add(expert);
        // if (expert == null) {
        //   System.out.println("An expert cannot be null");
        //   return;
        //  }
//    for(int i = 0; i<experts.size(); i++) {
//        if (this.experts.get(i) != null) {
//        System.out.println("This brand already has an expert: " + this.experts.get(i).getName());
//        return;
//        }
//        if (experts.get(i).getBrand() != null) {
//        System.out.println("This seller is already assigned to another brand: " + experts.get(i).getBrand().getName());
//        return;
//        }
//        if(!experts.get(i).getDepartment().equals(this.department)){
//        System.out.println("This expert is from another department");
//        return;
//        }
//
//        this.experts.add(expert);
//        expert.setBrand(this);
    }

//        System.out.println("Expert is set");

    public void addProduct(Product product) {
        products.add(product);
    }

    public String getDetails() {
            String result = "Brand ID: " + id +
                    ", Name: " + name +
                    ", Expert: " + experts.get(0).getName() + experts.get(1).getName() +
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
    public Department getDepartment() {
        return department;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Seller> getExperts() {
        return experts;
    }
}
