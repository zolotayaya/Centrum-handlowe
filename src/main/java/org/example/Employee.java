package org.example;

public abstract class Employee {
    private final int id;
    private final String name;
    private final Department department;
    private final float income;
    private final float commision;


    public Employee(int id,String name,Department department,float income,float commision){
        this.id=id;
        this.name=name;
        this.department=department;
        this.income=income;
        this.commision=commision;
    }
    //public abstract void calculate();
    public abstract String showInformation();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    public float getIncome() {
        return income;
    }

    public float getCommision() {
        return commision;
    }
}
