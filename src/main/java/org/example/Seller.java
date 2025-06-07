package org.example;

import java.sql.SQLException;

public class Seller extends Employee implements IPromotable {
    private int salesCount;
    private Brand brand;
    private float rating;
    private Manager promotedTo;


    public void addToPromotionQueue() {
    }

    public float getAvarageRating() {
        return 0;
    }

    public Seller(int id, String name, Department department, float income, float commision, int experience, int salesCount, float rating) {
        super(id, name, department, income, commision, experience);
        this.salesCount = salesCount;
        this.rating = rating;
        if(department!=null){
            getDepartment().addEmployee(this);
        }
    }

    public void saleProduct(Product product, int quantity) throws SQLException {
//        System.out.println("saleProduct called");
      if (promotedTo != null) {
            System.out.println("This seller has already been promoted and cannot sell anymore.");
            return;
        }
        product.updateQuantity(quantity);
        salesCount += 1;
        this.income+=product.getPrice()*getCommision()/100;
//        if (checkPromotionCondition()) {
//            executePromotion(); // автоматическое повышение
//        }
    }

    @Override
    public boolean checkPromotionCondition() {
        System.out.println("checkPromotionCondition called");
        Department department = this.getDepartment();
        System.out.println("Department: " + department.getName());
        if (department != null) {
            System.out.println("Number of sellers in department: " + department.getSellers().size());
        } else {
            System.out.println("Department is null");
            System.out.println(salesCount);
        }
        return salesCount >= 100 && department != null && department.getSellers().size() > 1;
    }

    @Override
    public void executePromotion() {
        System.out.println("executePromotion called");
        System.out.println("Sales count: " + salesCount);
        System.out.println("Promotion condition: " + checkPromotionCondition());
        Department department = getDepartment();
        if (department == null) return;
        System.out.print("Error1");

        if (brand != null) {
            brand.setExpert(null); // Или метод для удаления эксперта
            brand = null;
        }

        // Zwalniamy menegera
        Manager oldManager = department.getManager();
        if (oldManager != null) {
            department.fireManager();
        }

        // Tworzymy nowego menegera
        Manager newManager = new Manager(getId(),getName(),getDepartment(), getIncome(), getCommision(),getExperience());

        department.removeEmployee(this); // удаляем продавца
        department.addEmployee(newManager); // добавляем менеджера
        this.promotedTo = newManager;

        System.out.println(getName() + " Was promoted to manager!");

    }

    @Override
    public String showInformation() {
        return "Name: " + getName() + ", Department: " + getDepartment() + ", Income: " + getIncome() + ", Commision: " + getCommision() + ", Sales count: " + getsalesCount();
    }

    public void setBrand(Brand brand) {
        if (this.brand != null) {
            System.out.println("This seller is already assigned to the brand: " + this.brand.getName());
        }
        this.brand = brand;
    }

    public Brand getBrand() {
        return brand;
    }


    public int getsalesCount() {
        return  salesCount;
    }
    public Manager getPromotedTo() {
        return promotedTo;
    }
//    @Override
//    public float getIncome(){
//        return income;
//    }
}
