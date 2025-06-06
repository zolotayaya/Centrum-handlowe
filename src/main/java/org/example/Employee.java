package org.example;

public abstract class Employee {
    private int id;
    private final String name;
    private  final Department department;
    protected float income;
    private final float commision;
    private final int experience;


public Employee(int id,String name,Department department, float income,float commision, int experience){
        this.id=id;
        this.name=name;
        this.department=department;
        this.income=income;
        this.commision=commision;
        this.experience = experience ;
}
//public abstract void calculate();
public abstract String showInformation();

public Department setDepartment(String department){
    return new Department(department);
}

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
    public int getExperience() {return experience;}
    public void setIncome(float income) {
        this.income = income;
    }
}
