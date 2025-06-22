package org.example.app;

import org.example.model.DataExporter;
import org.example.model.HR_Service;
import org.example.dao.ReportingService;
import org.example.model.SaleSystem;
import org.example.dao.*;
import org.example.database.Database;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;
public class ShoppingCenter extends Window{
    private Database db;
    private Scanner scanner;
    private SaleSystem saleSystem;
    private ReportingService reportingService;
    private DataExporter dataExporter;
    private DepartmentDB department;
    private BrandDB brand;
    private BossDB boss;
    private ManagerDB manager;
    private SellerDB seller;
    private ProductDB product;


    public ShoppingCenter() {
        db = Database.getInstance();
        scanner = new Scanner(System.in);
        saleSystem = new SaleSystem();
        reportingService = new ReportingService();
        dataExporter = new DataExporter();
        department = new DepartmentDB();
        brand = new BrandDB();
        boss = new BossDB();
        manager = new ManagerDB();
        seller = new SellerDB();
        product = new ProductDB();
    }

    public void start() throws SQLException, UnsupportedEncodingException {
        initializeDatabase();
        selectUserRole();
    }

    private void selectUserRole() throws SQLException {
        while (true) {
            clearScreen();
            printHeader("Shopping Center Management System");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "1." + Colors.RESET.get() + " Enter as Customer");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "2." + Colors.RESET.get() + " Enter as Director");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "3." + Colors.RESET.get() + " Get a job");
            System.out.println(Colors.BOLD.get() + Colors.RED.get() + "4." + Colors.RESET.get() + " Exit System");
            System.out.print(Colors.BOLD.get() + "Select your role: " + Colors.RESET.get());

            int choice = getIntInput(1, 4);

            switch (choice) {
                case 1:
                    Customer.customerInterface(product,manager,seller,brand,saleSystem);
                    break;
                case 2:
                    Director.directorInterface(manager,seller,product,brand,saleSystem,reportingService,dataExporter);
                    break;
                case 3:
                    if(HR_Service.takeQuizDepartment()){
                        System.out.println(HR_Service.generateEmployeers(seller,department));
                    }
                    break;
                case 4:
                    System.out.println(Colors.GREEN.get() + "Exiting system..." + Colors.RESET.get());
                    return;
            }
        }
    }

    public void initializeDatabase() throws SQLException {
        printStatus("Initializing database...");
        db.cleanDB();
        db.initializationDB();
        System.out.println("Initialized allready database");
        department.setDepartmentsFromDB();
        manager.setManagersFromDB();
        seller.setSellersFromDB();
        brand.setBrandFromDB();
        brand.assignExpertsToBrands(seller.getSellers());
        boss.setBossFromDB();
        product.setProductsFromDB(brand);
        printSuccess("The database is ready for use.");
        pause();
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        try {
            ShoppingCenter app = new ShoppingCenter();
            app.start();
        } catch (SQLException | UnsupportedEncodingException e) {
            System.out.println(Colors.RED.get() + "Database Error: " + e.getMessage() + Colors.RESET.get());
        }
    }
}
