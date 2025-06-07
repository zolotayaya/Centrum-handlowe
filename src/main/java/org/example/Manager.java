package org.example;
import java.util.*;
public class Manager extends Employee {
    private List<Seller> ManagedSellers;


    public Manager(int id,String name, Department department, float income,float commision, int experience) {
        super(id, name, department,income, commision, experience);
        if (this.getDepartment().getManager() != null) {
            System.out.println("This department already has a manager: " + this.getDepartment().getManager().getName());
        }else {
            this.ManagedSellers = new ArrayList<>();
            this.getDepartment().setManager(this);
        }
    }
    public List<Seller> getSeller(){
        return ManagedSellers;
    }
    public void addSeller(Seller seller){
        ManagedSellers.add(seller);
    }
    public void removeSeller(int id){
        ManagedSellers.removeIf(seller -> seller.getId() == id);
    }
    @Override
    public String showInformation() {
        return "Name: " + getName() + ", Department: " + getDepartment() + ", Income: " + getIncome() + ", Commision: " + getCommision();
    }
    @Override
    public String toString() {
        return "Manager{name='" + getName() + "', id=" + getId() + "}";
    }

    public void updateIncome(Product product) {
        this.income += product.getPrice()*getCommision()/100;
    }

}
