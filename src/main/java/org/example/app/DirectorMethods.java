package org.example.app;

import org.example.dao.*;
import org.example.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DirectorMethods extends Window{
    private ManagerDB managerDB;
    private SellerDB sellerDB;
    private ProductDB productDB;
    private BrandDB brandDB;
    private SaleSystem salesystem;
    private ReportingService reportingService;
    private DataExporter dataExporter;

    public DirectorMethods(ManagerDB managers, SellerDB sellers, ProductDB products, BrandDB brands, SaleSystem salesystem, ReportingService reportingService, DataExporter dataExporter) {
        this.managerDB = managers;
        this.sellerDB = sellers;
        this.productDB = products;
        this.brandDB = brands;
        this.salesystem = salesystem;
        this.reportingService = reportingService;
        this.dataExporter = dataExporter;
    }
    public  void showEmployees(ManagerDB managers, SellerDB sellers) throws SQLException {
        clearScreen();
        printHeader("List of employees");

        printSubHeader("Managers");
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

    void showProducts(ProductDB products) throws SQLException {
        clearScreen();
        printHeader("Products");

        printTableHeader(new String[]{"Name", "Brand", "Department", "Price", "Quantity", "Description"});
        for (Product product : products.getProducts()) {
            System.out.println("Product ID:" + product.getId());
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

    private String shortenString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength-3) + "...";
    }

    void showBrands(BrandDB brands) {
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

    void simulateSales(ProductDB products, SellerDB sellers, SaleSystem saleSystem, BrandDB brands) throws SQLException {
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
        //System.out.println("Experience setted");
        List<Brand> brandList = brands.getBrands();
        //System.out.println("Brands setted");
        List<Seller> sellerList = sellers.getSellers();
        //System.out.println("Sellers setted");
        Set<Seller> assignedSellers = new HashSet<>();

        printStatus("Start of sales simulation..");
        for(Brand brand : brandList) {
            for (Seller seller : sellerList) {
                if (!assignedSellers.contains(seller) &&
                        seller.getDepartment() != null &&
                        brand.getDepartment() != null &&
                        seller.getDepartment().getName().equals(brand.getDepartment().getName())) {

                    brand.setExpert(seller);
                    assignedSellers.add(seller);
                    break;
                }
            }
        }
//        PurchaseHistory purchaseHistory = new PurchaseHistory();
        for (int w = 0; w < weeks; w++) {
            for (int d = 0; d < days; d++) {
                for (int h = 0; h < hours; h++) {
                    Product randomProduct = products.getProducts().get((int)(Math.random() * products.getProducts().size()));
                    Brand brand = randomProduct.getBrand();
                    List<Seller> experts = brand.getExperts();
                    if(experts == null || experts.isEmpty()) {
                        printError("Error");
                        return;
                    }
                    Seller seller = experts.get((int) (Math.random() * experts.size()));
                    int maxPerPurchase = Math.min(randomProduct.getQuantity(), 10);
                    int randomQuantity = new Random().nextInt(maxPerPurchase) + 1;
                    int randomId = new Random().nextInt(9000) + 1000; //  ID
                    saleSystem.processPurchase(randomProduct, seller, randomQuantity, randomId);
                    products.updateQuantityInDB(randomProduct, randomProduct.getQuantity());
                    SellerDB.updateSellerStats(seller);
                    Manager manager = seller.getDepartment().getManager();
                    if (manager != null) {
                        ManagerDB.updateManagerIncome(manager);
                    }

                    Boss boss = BossDB.getBoss();
                    if (boss != null) {
                        BossDB bossDB =new BossDB();
                        bossDB.updateIncomeInDatabase();
                    }

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
    void generateReports(ReportingService reportingService, DataExporter dataExporter) {
        clearScreen();
        printHeader("Select Report to Generate");

        System.out.println("1. Employee Report");
        System.out.println("2. Financial Report");
        System.out.println("3. Product Report");
        System.out.println("4. All Reports");
        System.out.print("\nEnter your choice (1-4): ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        try {
            BrandDB brandDB = new BrandDB();
            brandDB.setBrandFromDB();

            ProductDB productDB = new ProductDB();
            productDB.setProductsFromDB(brandDB);

            switch (choice) {
                case "1":
                    printStatus("Creating employee report...");
                    List<Map<String, Object>> employeeReport = reportingService.employeeReportWithPosition();
                    String employeeFileName = "employee_report_" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";

                    dataExporter.exportEmployeeReport(employeeReport, employeeFileName);
                    printSuccess("Employee report generated: " + employeeFileName);
                    break;
                case "2":
                    printStatus("Creating financial report...");

                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    LocalDate startDate = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    LocalDate endDate = LocalDate.parse(scanner.nextLine());
                    List<Map<String, Object>> financialReport = reportingService.generateFinancialSummary(startDate, endDate);
                    String financialFileName = "financial_report_" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";

                    dataExporter.exportFinancialSummary(financialReport, financialFileName);
                    printSuccess("Financial report generated: " + financialFileName);
                    break;
                case "3":
                    printStatus("Creating product report...");
                    List<Map<String, Object>> productReport = reportingService.generateProductReport(productDB, brandDB);
                    String fileName = "product_report_" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";

                    dataExporter.exportProductReport(productReport, fileName);
                    printSuccess("Product report generated:" + fileName);
                    break;
                case "4":
                    printStatus("Creating employee report...");
                    List<Map<String, Object>> empReport = reportingService.employeeReportWithPosition();
                    String employeeFile = "employee_report_" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";
                    dataExporter.exportEmployeeReport(empReport, employeeFile);

                    printStatus("Creating financial report...");
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    LocalDate startDateBatch = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    LocalDate endDateBatch = LocalDate.parse(scanner.nextLine());
                    List<Map<String, Object>> finReport = reportingService.generateFinancialSummary(startDateBatch, endDateBatch);
                    String financialFile = "financial_report_" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";
                    dataExporter.exportFinancialSummary(finReport, financialFile);

                    printStatus("Creating product report...");
                    List<Map<String, Object>> prodReport = reportingService.generateProductReport(productDB, brandDB);
                    String productFile = "product_report_" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";
                    dataExporter.exportProductReport(prodReport, productFile);

                    printSuccess("All reports generated successfully!");
                    System.out.println(Colors.BOLD.get() + "Created files:" + Colors.RESET.get());
                    System.out.println("• " + Colors.GREEN.get() + employeeFile + Colors.RESET.get());
                    System.out.println("• " + Colors.GREEN.get() + financialFile + Colors.RESET.get());
                    System.out.println("• " + Colors.GREEN.get() + productFile + Colors.RESET.get());
                    break;
                default:
                    printError("Invalid choice. Please enter a number from 1 to 4.");
            }
        } catch (Exception e) {
            printError("Error generating reports: " + e.getMessage());
            e.printStackTrace();
        }

        pause();
    }
}
