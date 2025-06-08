package org.example.model;

import java.util.*;

public class Boss{
    private static Boss instance;
    private String name;
    private float income;
    private List<Employee> allEmployees;
    private List<Department> allDepartment;
    private final int id = 1;

    public String makeReport() {
        return "Hello";
    }


    public Boss(String name, float income) {
        this.name=name;
        this.income=income;
        this.allEmployees = new ArrayList<>();
        this.allDepartment = new ArrayList<>();
    }
//    public static Boss getInstance() {
//        if (instance == null) {
//            try{
//                 PreparedStatement stmt = conn.prepareStatement("SELECT name, income FROM boss WHERE id = 1");
//                 ResultSet rs = stmt.executeQuery()) {
//
//                if (rs.next()) {
//                    String name = rs.getString("name");
//                    float income = rs.getFloat("income");
//                    instance = new Boss(name, income);
//                } else {
//                    instance = new Boss("Default Boss", 0);
//
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                instance = new Boss("Default Boss", 0);
//            }
//        }
//        return instance;
//    }
    public void addDepartment(Department department) {
        if (!allDepartment.contains(department)) {
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
    public void addIncome(float amount) {
        this.income += amount;
//        BossDB.updateIncomeInDatabase();
    }
public float getIncome() {
        return income;
}
//    private void updateIncomeInDatabase() {
//        try{
//             PreparedStatement stmt = conn.prepareStatement("UPDATE Boss SET income = ? WHERE id = 1"));
//            stmt.setFloat(1, income);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public float calculateCenterIncome() {
        float Total =this.income;
        for(Employee i : allEmployees){
            Total+= i.getIncome();
        }
        return Total;
    }
}
