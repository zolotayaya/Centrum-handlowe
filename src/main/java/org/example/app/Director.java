package org.example.app;
import org.example.dao.*;
import org.example.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 * Główna klasa interfejsu dyrektora — umożliwia zarządzanie pracownikami,
 * produktami, markami oraz generowanie raportów.
 */
public class Director extends Window {
    private PurchaseHistory purchaseHistory;
    private static int reportCounter = 1;  // Licznik unikalnych nazw plików
    /**
     * Wyświetla główne menu interfejsu dyrektora.
     *
     * @param managers          baza danych menedżerów
     * @param sellers           baza danych sprzedawców
     * @param products          baza danych produktów
     * @param brands            baza danych marek
     * @param salesystem        system sprzedaży
     * @param reportingService  usługa generowania raportów
     * @param dataExporter      moduł eksportu danych
     * @throws SQLException w przypadku problemu z bazą danych
     */
    public static void directorInterface(ManagerDB managers, SellerDB sellers, ProductDB products, BrandDB brands, SaleSystem salesystem, ReportingService reportingService, DataExporter dataExporter) throws SQLException {

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
                    Director.showEmployees(managers, sellers);
                    break;
                case 2:
                    Director.showProducts(products);
                    break;
                case 3:
                    Director.showBrands(brands);
                    break;
                case 4:
                    Director.simulateSales(products,sellers,salesystem,brands);
                    break;
                case 5:
                    Director.generateReports(reportingService,dataExporter);
                    break;
                case 6:
                    return;
            }
        }
    }
    /**
     * Wyświetla listę wszystkich pracowników (menedżerów i sprzedawców).
     *
     * @param managers baza danych menedżerów
     * @param sellers  baza danych sprzedawców
     * @throws SQLException w przypadku problemu z bazą danych
     */
    public static void showEmployees(ManagerDB managers, SellerDB sellers) throws SQLException {
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
    /**
     * Wyświetla wszystkie produkty z informacjami szczegółowymi.
     *
     * @param products baza danych produktów
     * @throws SQLException w przypadku problemu z bazą danych
     */
   public static void showProducts(ProductDB products) throws SQLException {
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
    /**
     * Skraca zbyt długi ciąg tekstowy do określonej długości.
     *
     * @param str       tekst wejściowy
     * @param maxLength maksymalna długość
     * @return skrócony tekst
     */
    private static String shortenString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength-3) + "...";
    }
    /**
     * Wyświetla listę wszystkich marek i przypisanych do nich ekspertów oraz produktów.
     *
     * @param brands baza danych marek
     */
    public static void showBrands(BrandDB brands) {
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
    /**
     * Symuluje sprzedaż produktów przez losowych sprzedawców w wybranym przedziale czasowym.
     *
     * @param products   baza danych produktów
     * @param sellers    baza danych sprzedawców
     * @param saleSystem system sprzedaży
     * @param brands     baza danych marek
     * @throws SQLException w przypadku problemu z bazą danych
     */
    public static void simulateSales(ProductDB products, SellerDB sellers, SaleSystem saleSystem, BrandDB brands) throws SQLException {
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

        sellers.setExperience(minExp, maxExp);// Ustawianie doświadczenia sprzedawców na podstawie zakresu
        List<Brand> brandList = brands.getBrands();// Pobranie listy marek i sprzedawców z bazy danych
        List<Seller> sellerList = sellers.getSellers();
        Set<Seller> assignedSellers = new HashSet<>();


        printStatus("Start of sales simulation..");
        // Symulacja sprzedaży przez tygodnie, dni i godziny
        for (int w = 0; w < weeks; w++) {
            for (int d = 0; d < days; d++) {
                for (int h = 0; h < hours; h++) {
                    // Losowy wybór produktu
                    Product randomProduct = products.getProducts().get((int)(Math.random() * products.getProducts().size()));
                    Brand brand = randomProduct.getBrand();
                    List<Seller> experts = brand.getExperts();
                    if(experts == null || experts.isEmpty()) {
                        printError("Error");
                        return;
                    }
                    // Losowy wybór eksperta (sprzedawcy)
                    Seller seller = experts.get((int) (Math.random() * experts.size()));
                    int maxPerPurchase = Math.min(randomProduct.getQuantity(), 10);// Losowa ilość sprzedanych produktów (maksymalnie 10)
                    int randomQuantity = new Random().nextInt(maxPerPurchase) + 1;
                    int randomId = new Random().nextInt(9000) + 1000; //  Losowy ID zakupu
                    saleSystem.processPurchase(randomProduct, seller, randomQuantity, randomId);// Przetwarzanie zakupu
                    products.updateQuantityInDB(randomProduct, randomProduct.getQuantity());
                    SellerDB.updateSellerStats(seller);// Aktualizacja statystyk sprzedawcy
                    Manager manager = seller.getDepartment().getManager();// Aktualizacja dochodu menedżera działu
                    if (manager != null) {
                        ManagerDB.updateManagerIncome(manager);
                    }

                    Boss boss = BossDB.getBoss();// Aktualizacja dochodu boss
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
    /**
     * Generuje raporty: pracowniczy, finansowy, produktowy lub wszystkie jednocześnie.
     *
     * @param reportingService usługa generowania raportów
     * @param dataExporter     moduł eksportu danych
     */
    public static void generateReports(ReportingService reportingService, DataExporter dataExporter) {
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
                    printStatus("Creating employee report...");// Tworzenie raportu pracowniczego
                    List<Map<String, Object>> employeeReport = reportingService.employeeReportWithPosition();
                    String employeeFileName = "employee_report_" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";

                    dataExporter.exportEmployeeReport(employeeReport, employeeFileName);
                    printSuccess("Employee report generated: " + employeeFileName);
                    break;
                case "2":
                    printStatus("Creating financial report..."); // Tworzenie raportu finansowego z podanym zakresem dat

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
                    printStatus("Creating product report...");// Tworzenie raportu produktów
                    List<Map<String, Object>> productReport = reportingService.generateProductReport(productDB, brandDB);
                    String fileName = "product_report_" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";

                    dataExporter.exportProductReport(productReport, fileName);
                    printSuccess("Product report generated:" + fileName);
                    break;
                case "4":
                    printStatus("Creating employee report...");// Tworzenie wszystkich raportów jednocześnie
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
