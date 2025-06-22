package org.example.model;
import org.example.app.Colors;
import org.example.dao.DepartmentDB;
import org.example.dao.HRServiceDB;
import org.example.dao.SellerDB;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.*;
public class HR_Service {
    private static HRServiceDB service;

    public static boolean takeQuizDepartment() throws SQLException {
        service = new HRServiceDB();
        service.initialiseQuize();
        Scanner scanner = new Scanner(System.in);
        int right_Ansers = 0;
        System.out.println(Colors.BOLD.get() + Colors.GREEN.get() + "Starting Quiz " + Colors.YELLOW.get() + "( odpowiedz 'TAK' lub 'NIE')");
        for(int i = 0; i<service.getQuestions().size(); i++){
                System.out.println(Colors.CYAN.get() + service.getQuestions().get(i));
                String your_Answer = scanner.nextLine();
                if (your_Answer.toLowerCase().equals(service.getAnswers().get(i))) {
                    right_Ansers++;
                }
        }
        if(right_Ansers >= 5){
            System.out.println("Quiz finished. You have " + right_Ansers + " questions left");
            return true;
        }else{
            System.out.println("Unfortunately you have " + right_Ansers + " questions left. That too small. Try inn the next time :)");
            return false;
        }
    }
    public static String generateEmployeers(SellerDB sellers, DepartmentDB departments) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employeer name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your experience years");
        int years = scanner.nextInt();
        scanner.nextLine();
        boolean found = true;
        String department = null;
        while(found) {
            System.out.println("Select department (Electronics, Clothes, Shoes, Cosmetics, Garden Tools): ");
            String selectedDepartment = scanner.nextLine();
            for (Department dept : departments.getDepartments()) {
                if (selectedDepartment.equalsIgnoreCase(dept.getName())) {
                    department = dept.getName();
                    found = false;
                    break;
                }
            }

            if (found) {
                System.out.println("Department not found. Please try again.");
            } else {
                System.out.println("Selected department: " + department);
            }
        }
        int id_employee = service.create_Employee_In_DB(name,years,department);
        SellerDB sellers_db = SellerDB.getInstance();
        Seller seller = new Seller(id_employee,name,getDep(department,departments),0,10,0,0,years);
        sellers_db.updateSeller(seller);
        String message = "Employee " + name + " has been created";
        return message;
    }
    public static Department getDep(String department_name,DepartmentDB departments) throws SQLException {
        for(Department department: departments.getDepartments()){
            if(department.getName().equals(department_name)){
                return department;
            }
        }
        return null;
    }

}
