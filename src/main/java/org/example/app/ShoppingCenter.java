package org.example.app;

import org.example.DataExporter;
import org.example.model.HR_Service;
import org.example.model.ReportingService;
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


    // ANSI color codes for console output

//    private static final String RESET = "\u001B[0m";
//    private static final String RED = "\u001B[31m";
//    private static final String GREEN = "\u001B[32m";
//    private static final String YELLOW = "\u001B[33m";
//    private static final String BLUE = "\u001B[34m";
//    private static final String PURPLE = "\u001B[35m";
//    private static final String CYAN = "\u001B[36m";
//    private static final String BOLD = "\u001B[1m";

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

            int choice = getIntInput(1, 3);

            switch (choice) {
                case 1:
                    Customer.customerInterface(product);
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

//    private void customerInterface() throws SQLException {
//        while (true) {
//            clearScreen();
//            printHeader("Customer Portal");
//            System.out.println(BOLD + BLUE + "1." + RESET + " View Available Products");
//            System.out.println(BOLD + BLUE + "2." + RESET + " Purchase a Product");
//            System.out.println(BOLD + BLUE + "3." + RESET + " Leave a Product Review");
//            System.out.println(BOLD + BLUE + "4." + RESET + " See products Review");
//            System.out.println(BOLD + RED + "5." + RESET + " Return to Main Menu");
//            System.out.print(BOLD + "Select option: " + RESET);
//
//            int choice = getIntInput(1, 4);
//
//            switch (choice) {
//                case 1:
//                    showProducts();
//                    break;
//                case 2:
//                    // purchaseProduct() method to be implemented
//                    System.out.println(YELLOW + "Purchase functionality coming soon!" + RESET);
//                    pause();
//                    break;
//                case 3:
//                    leaveReview();
//                    System.out.println(YELLOW + "Review functionality coming soon!" + RESET);
//                    pause();
//                    break;
//                case 4:
//                    viewProductReviews();
//                    break;
//                case 5:
//                    return;
//            }
//        }
//    }

//    private void directorInterface() throws SQLException {
//        // This is your original main menu functionality
//        while (true) {
//            clearScreen();
//            printHeader("Director Management Portal");
//            System.out.println(BOLD + BLUE + "1." + RESET + " Show Employees");
//            System.out.println(BOLD + BLUE + "2." + RESET + " Show Products");
//            System.out.println(BOLD + BLUE + "3." + RESET + " Show Brands");
//            System.out.println(BOLD + BLUE + "4." + RESET + " Simulate Sales");
//            System.out.println(BOLD + BLUE + "5." + RESET + " Generate Reports");
//            System.out.println(BOLD + RED + "6." + RESET + " Return to Main Menu");
//            System.out.print(BOLD + "Select action: " + RESET);
//
//            int choice = getIntInput(1, 6);
//
//            switch (choice) {
//                case 1:
//                    showEmployees(seller,manager);
//                    break;
//                case 2:
//                    showProducts();
//                    break;
//                case 3:
//                    showBrands();
//                    break;
//                case 4:
//                    simulateSales();
//                    break;
//                case 5:
//                    generateReports();
//                    break;
//                case 6:
//                    return;
//            }
//        }
//    }


    private void initializeDatabase() throws SQLException {
        printStatus("Initializing database...");
        db.cleanDB();
//        System.out.println("Deleted");
        db.initializationDB();
        System.out.println("Initialized allready database");
        department.setDepartmentsFromDB();
        manager.setManagersFromDB();
        seller.setSellersFromDB();
        brand.setBrandFromDB();
        boss.setBossFromDB();
        product.setProductsFromDB(brand);
        printSuccess("The database is ready for use.");
        pause();
    }


//    private void leaveReview() throws SQLException {
//        clearScreen();
//        printHeader("Leave Product Review");
//
//        if (db.getProducts().isEmpty()) {
//            printError("No products available for review!");
//            pause();
//            return;
//        }
//
//        // Показываем продукты с их ID
//        System.out.println(BOLD + "Available Products:" + RESET);
//        for (int i = 0; i < db.getProducts().size(); i++) {
//            Product p = db.getProducts().get(i);
//            System.out.printf("%s%d.%s %s (ID: %d)\n",
//                    BOLD, i + 1, RESET, p.getName(), p.getId());
//        }
//
//        System.out.print("\nSelect product number: ");
//        int productIndex = getIntInput(1, db.getProducts().size()) - 1;
//        Product selectedProduct = db.getProducts().get(productIndex);
//
//        System.out.print("Enter rating (1-5): ");
//        int rating = getIntInput(1, 5);
//
//        System.out.print("Enter your comment: ");
//        scanner.nextLine(); // Очистка буфера
//        String comment = scanner.nextLine();
//
//        // Сохраняем отзыв с ID и названием продукта
//        Review.saveReview(selectedProduct.getId(), selectedProduct.getName(),
//                rating, comment);
//
//        printSuccess("Thank you for your review!");
//        pause();
//    }

//    private void viewProductReviews() throws SQLException {
//        clearScreen();
//        printHeader("Product Reviews");
//
//        if (db.getProducts().isEmpty()) {
//            printError("No products available!");
//            pause();
//            return;
//        }
//
//        // Показываем продукты с их ID
//        System.out.println(BOLD + "Available Products:" + RESET);
//        for (int i = 0; i < db.getProducts().size(); i++) {
//            Product p = db.getProducts().get(i);
//            System.out.printf("%s%d.%s %s (ID: %d)\n",
//                    BOLD, i + 1, RESET, p.getName(), p.getId());
//        }
//
//        System.out.print("\nSelect product number: ");
//        int productIndex = getIntInput(1, db.getProducts().size()) - 1;
//        Product selectedProduct = db.getProducts().get(productIndex);
//
//        // Получаем отзывы по ID продукта
//        List<Review> reviews = Review.getReviewsByProductId(selectedProduct.getId());
//
//        clearScreen();
//        printHeader("Reviews for: " + selectedProduct.getName());
//
//        if (reviews.isEmpty()) {
//            printError("No reviews yet for this product!");
//        } else {
//            printTableHeader(new String[]{"Rating", "Date", "Comment"});
//            for (Review review : reviews) {
//                printTableRow(new String[]{
//                        String.valueOf(review.getRating()),
//                        review.getDate(),
//                        review.getComment()
//                }, CYAN);
//            }
//        }
//        pause();
//    }

//    private void showEmployees() {
//        clearScreen();
//        printHeader("List of employees");
//
//
//        printTableHeader(new String[]{"ID", "Name", "Department", "Income", "Experience"});
//        for (Manager manager : db.getManagers()) {
//            printTableRow(new String[]{
//                    String.valueOf(manager.getId()),
//                    manager.getName(),
//                    manager.getDepartment().getName(),
//                    String.format("%.2f", manager.getIncome()),
//                    String.valueOf(manager.getExperience())
//            }, YELLOW);
//        }
//
//
//        System.out.println();
//        printSubHeader("Sellers");
//        printTableHeader(new String[]{"ID", "Name", "Department", "Sales_count", "Rating", "Experience"});
//        for (Seller seller : db.getSellers()) {
//            printTableRow(new String[]{
//                    String.valueOf(seller.getId()),
//                    seller.getName(),
//                    seller.getDepartment().getName(),
//                    String.valueOf(seller.getsalesCount()),
//                    String.format("%.1f", seller.getRating()),
//                    String.valueOf(seller.getExperience())
//            }, CYAN);
//        }
//
//        pause();
//    }
//
//    private void showProducts() {
//        clearScreen();
//        printHeader("Products");
//
//        printTableHeader(new String[]{"Name", "Brand", "Department", "Price", "Quantity", "Description"});
//        for (Product product : product.getProducts()) {
//            printTableRow(new String[]{
//                    product.getName(),
//                    product.getBrand().getName(),
//                    product.getBrand().getDepartment().getName(),
//                    String.format("%.2f", product.getPrice()),
//                    String.valueOf(product.getQuantity()),
//                    shortenString(product.getDescription(), 20)
//            }, GREEN);
//        }
//
//        pause();
//    }

//    private void showBrands() {
//        clearScreen();
//        printHeader("Brands");
//
//        for (Brand brand : db.getBrands()) {
//            System.out.println(PURPLE + BOLD + "▶ " + brand.getName() + RESET +
//                    " (Department: " + brand.getDepartment().getName() + ")");
//
//            System.out.println(BOLD + "  Experts:" + RESET);
//            if (brand.getExperts().isEmpty()) {
//                System.out.println("    " + RED + "No experts" + RESET);
//            } else {
//                for (Seller expert : brand.getExperts()) {
//                    System.out.println("    • " + expert.getName() +
//                            " (Experience: " + expert.getExperience() + " years, Rating: " +
//                            String.format("%.1f", expert.getRating()) + ")");
//                }
//            }
//
//            System.out.println(BOLD + "  Products:" + RESET);
//            if (brand.getProducts().isEmpty()) {
//                System.out.println("    " + RED + "No products" + RESET);
//            } else {
//                for (Product product : brand.getProducts()) {
//                    System.out.println("    • " + product.getName() +
//                            " (Price: " + String.format("%.2f", product.getPrice()) +
//                            ", Quantity: " + product.getQuantity() + ")");
//                }
//            }
//            System.out.println();
//        }
//
//        pause();
//    }
//
//    private void simulateSales() throws SQLException {
//        clearScreen();
//        printHeader("Sales simulation");
//
//        System.out.print("Enter the number of hours worked: ");
//        int hours = getIntInput(1, 24);
//
//        System.out.print("Enter number of days: ");
//        int days = getIntInput(1, 30);
//
//        System.out.print("Enter number of weeks: ");
//        int weeks = getIntInput(1, 52);
//
//        System.out.print("Minimum experience of sellers: ");
//        int minExp = getIntInput(0, 50);
//
//        System.out.print("Maximum sales experience: ");
//        int maxExp = getIntInput(minExp, 50);
//
//        seller.setExperience(minExp, maxExp);
//
//        printStatus("Start of sales simulation..");
//
//        for (int w = 0; w < weeks; w++) {
//            for (int d = 0; d < days; d++) {
//                for (int h = 0; h < hours; h++) {
//                    Product randomProduct = product.getProducts().get((int)(Math.random() * product.getProducts().size()));
//                    Brand brand = randomProduct.getBrand();
//                    List<Seller> experts = brand.getExperts();
//                    Seller seller = experts.get((int)(Math.random() * experts.size()));
//
//                    saleSystem.processPurchase(randomProduct, seller, 1, 1);
//                    product.updateQuantityInDB(randomProduct, randomProduct.getQuantity());
//
//                    System.out.printf("%s[Week %d, Day %d, Hour %d]%s Sold: %s%s%s by seller %s%s%s, Remaining: %s%d%s\n",
//                            BLUE, w+1, d+1, h+1, RESET,
//                            GREEN, randomProduct.getName(), RESET,
//                            YELLOW, seller.getName(), RESET,
//                            CYAN, randomProduct.getQuantity(), RESET);
//                }
//            }
//        }
//
//        printSuccess("Simulation completed successfully!");
//        pause();
//    }
//
//    private void generateReports() {
//        clearScreen();
//        printHeader("Generating reports");
//
//        try {
//            printStatus("Creating a report on employees...");
//            List<Map<String, Object>> employeeReport = reportingService.employeeReportWithPosition();
//            dataExporter.exportEmployeeReport(employeeReport, "employee_report.csv");
//
//            printStatus("Creating a financial report...");
//            List<Map<String, Object>> financialReport = reportingService.generateFinancialSummary();
//            dataExporter.exportFinancialSummary(financialReport, "financial_report.csv");
//
//            printStatus("Creating a product report...");
//            List<Map<String, Object>> productReport = reportingService.generateProductReport();
//            dataExporter.exportProductReport(productReport, "product_report.csv");
//
//            printSuccess("All reports generated successfully!");
//            System.out.println(BOLD + "Created files:" + RESET);
//            System.out.println("• " + GREEN + "employee_report.csv" + RESET);
//            System.out.println("• " + GREEN + "financial_report.csv" + RESET);
//            System.out.println("• " + GREEN + "product_report.csv" + RESET);
//        } catch (Exception e) {
//            printError("Error when generating reports: " + e.getMessage());
//        }
//
//        pause();
//    }
//
//
//    private void clearScreen() {
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//    }

//    private void printHeader(String text) {
//        System.out.println(Colors.BOLD.get() + Colors.PURPLE.get() + "=== " + text + " ===" + Colors.RESET.get());
//        System.out.println();
//    }

//    private void printSubHeader(String text) {
//        System.out.println(BOLD + CYAN + "◆ " + text + RESET);
//    }
//
//    private void printStatus(String text) {
//        System.out.println(Colors.BLUE.get() + "➤ " + text + Colors.RESET.get());
//    }
////
//    private void printSuccess(String text) {
//        System.out.println(Colors.GREEN.get() + "✓ " + text + Colors.RESET.get());
//    }
//
//    private void printError(String text) {
//        System.out.println(RED + "✗ " + text + RESET);
//    }
//
//    private void printTableHeader(String[] columns) {
//        System.out.print(BOLD + BLUE);
//        for (String col : columns) {
//            System.out.printf("│ %-15s ", col);
//        }
//        System.out.println("│" + RESET);
//        System.out.println(BLUE + "├" + "─────────────────┼".repeat(columns.length-1) + "─────────────────┤" + RESET);
//    }
//
//    private void printTableRow(String[] values, String color) {
//        System.out.print(color);
//        for (String val : values) {
//            System.out.printf("│ %-15s ", val);
//        }
//        System.out.println("│" + RESET);
//    }
//
//    private String shortenString(String str, int maxLength) {
//        if (str == null) return "";
//        if (str.length() <= maxLength) return str;
//        return str.substring(0, maxLength-3) + "...";
//    }
//
//    private void pause() {
//        System.out.println();
//        System.out.print(Colors.BOLD.get() + "Press Enter to continue..." + Colors.RESET.get());
//        scanner.nextLine();
//    }
//
//    private int getIntInput(int min, int max) {
//        while (true) {
//            try {
//                int input = scanner.nextInt();
//                scanner.nextLine();
//                if (input >= min && input <= max) {
//                    return input;
//                }
//                System.out.println(Colors.RED.get() + "Please enter a number from " + min + " to " + max + Colors.RESET.get());
//            } catch (Exception e) {
//                scanner.nextLine();
//                System.out.println(Colors.RED.get() + "Invalid input. Please enter an integer.." + Colors.RESET.get());
//            }
//}

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
