package org.example.app;

import org.example.DataExporter;
import org.example.ReportingService;
import org.example.SaleSystem;
import org.example.dao.BrandDB;
import org.example.dao.ManagerDB;
import org.example.dao.ProductDB;
import org.example.dao.SellerDB;
import org.example.model.Brand;
import org.example.model.Manager;
import org.example.model.Product;
import org.example.model.Seller;

import javax.naming.BinaryRefAddr;
import java.sql.SQLException;
import java.util.*;

import static org.example.dao.ManagerDB.managers;

public class Director extends Window {
    public static void directorInterface(ManagerDB managers, SellerDB sellers, ProductDB products, BrandDB brands, SaleSystem salesystem, ReportingService reportingService, DataExporter dataExporter) throws SQLException {
        // This is your original main menu functionality
        while (true) {
            clearScreen();
            printHeader("Director Management Portal");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "1." + Colors.RESET.get() + " Show Employees");
            System.out.println(Colors.BOLD.get() +  Colors.BLUE.get() + "2." + Colors.RESET.get() + " Show Products");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "3." + Colors.RESET.get() + " Show Brands");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "4." + Colors.RESET.get() + " Simulate Sales");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "5." + Colors.RESET.get() + " Generate Reports");
            System.out.println(Colors.BOLD.get() + Colors.RED.get() + "6." + Colors.RESET.get() + " Return to Main Menu");
            System.out.print(Colors.BOLD.get() + "Select action: " + Colors.RESET.get());

            int choice = getIntInput(1, 6);

            switch (choice) {
                case 1:
                    showEmployees(managers, sellers);
                    break;
                case 2:
                    showProducts(products);
                    break;
                case 3:
                    showBrands(brands);
                    break;
                case 4:
                    simulateSales(products,sellers,salesystem,brands);
                    break;
                case 5:
                    generateReports(reportingService,dataExporter);
                    break;
                case 6:
                    return;
            }
        }
    }
    private static void showEmployees(ManagerDB managers, SellerDB sellers) throws SQLException {
        clearScreen();
        printHeader("List of employees");


        printTableHeader(new String[]{"ID", "Name", "Department", "Income", "Experience"});
        for (Manager manager : managers.getManagers()) {
            printTableRow(new String[]{
                    String.valueOf(manager.getId()),
                    manager.getName(),
                    manager.getDepartment().getName(),
                    String.format("%.2f", manager.getIncome()),
                    String.valueOf(manager.getExperience())
            }, Colors.YELLOW.get());
        }


        System.out.println();
        printSubHeader("Sellers");
        printTableHeader(new String[]{"ID", "Name", "Department", "Sales_count", "Rating", "Experience"});
        for (Seller seller : sellers.getSellers()) {
            printTableRow(new String[]{
                    String.valueOf(seller.getId()),
                    seller.getName(),
                    seller.getDepartment().getName(),
                    String.valueOf(seller.getsalesCount()),
                    String.format("%.1f", seller.getRating()),
                    String.valueOf(seller.getExperience())
            }, Colors.CYAN.get());
        }

        pause();
    }

    private static void showProducts(ProductDB products) throws SQLException {
        clearScreen();
        printHeader("Products");

        printTableHeader(new String[]{"Name", "Brand", "Department", "Price", "Quantity", "Description"});
        for (Product product : products.getProducts()) {
            printTableRow(new String[]{
                    product.getName(),
                    product.getBrand().getName(),
                    product.getBrand().getDepartment().getName(),
                    String.format("%.2f", product.getPrice()),
                    String.valueOf(product.getQuantity()),
                    shortenString(product.getDescription(), 20)
            }, Colors.GREEN.get());
        }
        pause();
    }

    private static String shortenString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength-3) + "...";
    }

    private static void showBrands(BrandDB brands) {
        clearScreen();
        printHeader("Brands");

        for (Brand brand : brands.getBrands()) {
            System.out.println(Colors.PURPLE.get() + Colors.BOLD.get() + "▶ " + brand.getName() + Colors.RESET.get() +
                    " (Department: " + brand.getDepartment().getName() + ")");

            System.out.println(Colors.BOLD.get() + "  Experts:" + Colors.RESET.get());
            if (brand.getExperts().isEmpty()) {
                System.out.println("    " + Colors.RED.get() + "No experts" + Colors.RESET.get());
            } else {
                for (Seller expert : brand.getExperts()) {
                    System.out.println("    • " + expert.getName() +
                            " (Experience: " + expert.getExperience() + " years, Rating: " +
                            String.format("%.1f", expert.getRating()) + ")");
                }
            }

            System.out.println(Colors.BOLD.get() + "  Products:" + Colors.RESET.get());
            if (brand.getProducts().isEmpty()) {
                System.out.println("    " + Colors.RED.get() + "No products" + Colors.RESET.get());
            } else {
                for (Product product : brand.getProducts()) {
                    System.out.println("    • " + product.getName() +
                            " (Price: " + String.format("%.2f", product.getPrice()) +
                            ", Quantity: " + product.getQuantity() + ")");
                }
            }
            System.out.println();
        }

        pause();
    }

    private static void simulateSales(ProductDB products,SellerDB sellers, SaleSystem saleSystem,BrandDB brands) throws SQLException {
        clearScreen();
        printHeader("Sales simulation");

        System.out.print("Enter the number of hours worked: ");
        int hours = getIntInput(1, 24);

        System.out.print("Enter number of days: ");
        int days = getIntInput(1, 30);

        System.out.print("Enter number of weeks: ");
        int weeks = getIntInput(1, 52);

        System.out.print("Minimum experience of sellers: ");
        int minExp = getIntInput(0, 50);

        System.out.print("Maximum sales experience: ");
        int maxExp = getIntInput(minExp, 50);

        sellers.setExperience(minExp, maxExp);
List<Brand> brandList = brands.getBrands();
List<Seller> sellerList = sellers.getSellers();
        Set<Seller> assignedSellers = new HashSet<>();

        printStatus("Start of sales simulation..");
        for (Brand brand : brandList) {
            for (Seller seler : sellerList) {
                if (!assignedSellers.contains(seler) && Objects.equals(seler.getDepartment(), brand.getDepartment())) {
                    brand.setExpert(seler);
                    assignedSellers.add(seler);
                    break;
                }
            }
        }
        for (int w = 0; w < weeks; w++) {
            for (int d = 0; d < days; d++) {
                for (int h = 0; h < hours; h++) {
                    Product randomProduct = products.getProducts().get((int)(Math.random() * products.getProducts().size()));
                    Brand brand = randomProduct.getBrand();
                    List<Seller> experts = brand.getExperts();
                    System.out.println("Length of experts: " + experts.size());
                    Seller seller = experts.get((int)(Math.random() * experts.size()));

                    saleSystem.processPurchase(randomProduct, seller, 1, 1);
                    products.updateQuantityInDB(randomProduct, randomProduct.getQuantity());

                    System.out.printf("%s[Week %d, Day %d, Hour %d]%s Sold: %s%s%s by seller %s%s%s, Remaining: %s%d%s\n",
                            Colors.BLUE.get(), w+1, d+1, h+1, Colors.RESET.get(),
                            Colors.GREEN.get(), randomProduct.getName(), Colors.RESET.get(),
                            Colors.YELLOW.get(), seller.getName(), Colors.RESET.get(),
                            Colors.CYAN.get(), randomProduct.getQuantity(), Colors.RESET.get());
                }
            }
        }

        printSuccess("Simulation completed successfully!");
        pause();
    }

    private static void generateReports(ReportingService reportingService, DataExporter dataExporter) {
        clearScreen();
        printHeader("Generating reports");

        try {
            printStatus("Creating a report on employees...");
            List<Map<String, Object>> employeeReport = reportingService.employeeReportWithPosition();
            dataExporter.exportEmployeeReport(employeeReport, "employee_report.csv");

            printStatus("Creating a financial report...");
            List<Map<String, Object>> financialReport = reportingService.generateFinancialSummary();
            dataExporter.exportFinancialSummary(financialReport, "financial_report.csv");

            printStatus("Creating a product report...");
            List<Map<String, Object>> productReport = reportingService.generateProductReport();
            dataExporter.exportProductReport(productReport, "product_report.csv");

            printSuccess("All reports generated successfully!");
            System.out.println(Colors.BOLD.get() + "Created files:" + Colors.RESET.get());
            System.out.println("• " + Colors.GREEN.get() + "employee_report.csv" + Colors.RESET.get());
            System.out.println("• " +Colors.GREEN.get() + "financial_report.csv" + Colors.RESET.get());
            System.out.println("• " + Colors.GREEN.get() + "product_report.csv" + Colors.RESET.get());
        } catch (Exception e) {
            printError("Error when generating reports: " + e.getMessage());
        }

        pause();
    }

}
