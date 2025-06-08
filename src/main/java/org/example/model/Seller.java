package org.example.model;

import org.example.IPromotable;

public class Seller extends Employee implements IPromotable {
    private int salesCount;
    private Brand brand;
    private final float rating;
    private Manager promotedTo;


    public void addToPromotionQueue() {
    }

    public float getAvarageRating() {
        return 0;
    }

    public Seller(int id, String name, Department department, float income, float commision, int salesCount, float rating,int experience) {
        super(id, name,department, income, commision,experience);

        this.salesCount = salesCount;
        this.rating = rating;
        if(department!=null){
            department.addEmployee(this);
        }
    }



    public void saleProduct(Product product, int quantity) {
        System.out.println("saleProduct called");
        if (promotedTo != null) {
            System.out.println("This seller has already been promoted and cannot sell anymore.");
            return;
        }
        float totalPrice= product.getPrice()*quantity;
        product.updateQuantity(quantity);
        salesCount += 1;
        income+= totalPrice * (getCommision()/100f);

        Department department = getDepartment();
        if(department !=null){
            Manager manager = department.getManager();
            if (manager != null){
                manager.addIncome(totalPrice* (manager.getCommision()/100f));
            }
        }
//        Boss boss =Boss.getInstance();
//        boss.addIncome(totalPrice * 0.01f);

        if (checkPromotionCondition()) {
            executePromotion();
        }
    }

    @Override
    public boolean checkPromotionCondition() {
        System.out.println("checkPromotionCondition called");
        Department department = getDepartment();
        System.out.println("Department: " + department);
        if (department != null) {
            System.out.println("Number of sellers in department: " + department.getSellers().size());
        } else {
            System.out.println("Department is null");
            System.out.println(salesCount);
        }
        return salesCount >= 5 && department != null && department.getSellers().size() > 1;
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
            brand.setExpert(null); // Fire expert
            brand = null;
        }

        // Fire old manager
        Manager oldManager = department.getManager();
        if (oldManager != null) {
            department.fireManager();
        }


        Manager newManager = new Manager(getId(), getName(), department, getIncome(), getCommision(),getExperience());

        department.removeEmployee(this);
        department.addEmployee(newManager);
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
    @Override
    public float getIncome(){
        return income;
    }

    public float getRating(){
        return rating;
    }
}
