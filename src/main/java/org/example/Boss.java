package org.example;
import java.util.*;
public class Boss{
    private int id;
    private String name;
    private float income;
    private List<Employee> allEmployees;
    private List<Department> allDepartment;
    public String makeReport(){
        return "Hello";
    }
    public float calculateCenterIncome(){
        return 0;
    }
    public Boss() {
        this.allEmployees=new ArrayList<>();
        this.allDepartment=new ArrayList<>();
    }
    public void addDepartment(Department department){
        if(!allDepartment.contains(department)){
            allDepartment.add(department);
            department.getEmployees().forEach(this::addEmployeer);
        }
    }
    public void addEmployeer(Employee employee) {
        if (!allEmployees.contains(employee)) {
            allEmployees.add(employee);
            if (employee.getDepartment() != null && !allDepartment.contains(employee.getDepartment())) {
                addDepartment(employee.getDepartment());
            }
        }
    }



    public float getIncome() {
        return income;
    }
    public void updateIncome(float income) {
        this.income += income;
    }
}
