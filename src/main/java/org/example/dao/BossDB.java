package org.example.dao;

import org.example.model.Department;
import org.example.database.Database;
import org.example.model.Boss;
import org.example.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BossDB {
    private static Boss instance;
    private List<Employee> allEmployees;
    private List<Department> allDepartment;
    private Connection connection;

    public BossDB() {
        this.allEmployees = new ArrayList<>();
        this.allDepartment = new ArrayList<>();
        connection = Database.getConnection();
    }

    public String makeReport() {
        return "Hello";
    }

    public void setBossFromDB() throws SQLException {
        if (instance == null) {
            try{
                 PreparedStatement stmt = connection.prepareStatement("SELECT name, income FROM Boss WHERE id = 1");
                 ResultSet rs = stmt.executeQuery(

                 );

                if (rs.next()) {
                    String name = rs.getString("name");
                    float income = rs.getFloat("income");
                    instance = new Boss(name, income);
                } else {
                    instance = new Boss("Default Boss", 0);

                }
            } catch (SQLException e) {
                e.printStackTrace();
                instance = new Boss("Default Boss", 0);
            }
        }
    }
    public static Boss getBoss() {
        return instance;
    }
//
//    public void addDepartment(Department department) {
//        if (!allDepartment.contains(department)) {
//            allDepartment.add(department);
//            department.getEmployees().forEach(this::addEmployeer);
//        }
//    }
//
//    public void addEmployeer(Employee employee) {
//        if (!allEmployees.contains(employee)) {
//            allEmployees.add(employee);
//            if (employee.getDepartment() != null && !allDepartment.contains(employee.getDepartment())) {
//                addDepartment(employee.getDepartment());
//            }
//        }
//    }
//    public void addIncome(float amount) {
//        this.income += amount;
//        updateIncomeInDatabase();
//    }

    public  void updateIncomeInDatabase() throws SQLException {
             PreparedStatement stmt = connection.prepareStatement("UPDATE Boss SET income = ? WHERE id = 1");
            stmt.setFloat(1, instance.getIncome());
            stmt.executeUpdate();
    }

//    public float calculateCenterIncome() {
//        float Total =this.income;
//        for(Employee i : allEmployees){
//            Total+= i.getIncome();
//        }
//        return Total;
//    }
}