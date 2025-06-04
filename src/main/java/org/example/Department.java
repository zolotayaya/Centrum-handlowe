package org.example;
import java.util.*;
public class Department {
    private String name;
    private Manager manager;
    private List<Brand> brands;
    private List<Employee> employees;

    public Department(String name){
        this.name=name;
        this.brands=new ArrayList<>();
        this.employees=new ArrayList<>();
    }

    public List<Product> getProductsByBrande(Brand brand){
        return new ArrayList<>();
    }

    public Manager getManager(){
            return manager;
    }

    public List<Seller> getSellers() {
        List<Seller> sellers = new ArrayList<>();
        for (Employee e : employees) {
            if (e instanceof Seller) {
                sellers.add((Seller) e);
            }
        }
        return sellers;
    }

    public void setManager(Manager manager){
        if(this.manager !=null){
            return;
        }
        this.manager=manager;
        if(!employees.contains(manager)) {
            employees.add(manager);
//            System.out.println("New manager is set");
        }
//        this.manager=manager;
//        System.out.print("New manager is set");
    }

    public boolean addEmployee(Employee employee) {
        if (employee instanceof Manager) {
            if (getManager() != null) {
                System.out.println("There is already a manager in the department!:"+employee.getName());
                return false; // уже есть менеджер — отказать
            }
            setManager((Manager)employee);

        }
        employees.add(employee);
        return true;
    }

    public boolean checkSellerCountForPromotion() {
        return getSellers().size() > 1;
    }

    public void fireManager() {
        Manager manager = getManager();
        if (manager != null) {
            employees.remove(manager);
            System.out.println("Manager " + manager.getName() + " was fired.");
            manager=null;
        }
    }

    public void removeEmployee(Employee e) {
        employees.remove(e);
    }

    public String getName() {
        return name;
    }
    public List<Employee> getEmployees() {
        return employees;
    }


    //public void Metoda(){}
}
