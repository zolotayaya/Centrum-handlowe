package org.example;
import java.util.*;
public class Department {
    private String name;
    private List<Brand> brands;
    private List<Empoyee> empoyees;
    public List<Product> getProductsByBrande(Brand brand){
    return new ArrayList<>();
    }
    public Manager getManager(){
        return new Manager();
    }
    public List<Seller> getSellers(){
        return new ArrayList<>();
    }
    public boolean checkSellerCountForPromotion(){
        return false;
    }
    public void fireManager(){}
    public void Metoda(){}
}
