package org.example.model;

import org.example.dao.BossDB;

import java.sql.SQLException;
import java.time.LocalDate;
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
    public float calculateCenterIncome(LocalDate startDate, LocalDate endDate) {
        return new BossDB().CalculateTotalIncomeFromHistory( startDate, endDate);
    }

    public float calculateCenterIncome() {
        float Total =this.income;
        for(Employee i : allEmployees){
            Total+= i.getIncome();
        }
        return Total;
    }
    public static Boss getInstance()  {
        if (instance == null) {
            try {
                instance = BossDB.setBossFromDB(); // загрузи из базы
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
    public String getName() {
        return name;
    }

}
