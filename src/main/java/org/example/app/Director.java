package org.example.app;
import org.example.app.DirectorMethods;
import org.example.dao.*;
import org.example.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Director extends Window {
    private PurchaseHistory purchaseHistory;
    private static int reportCounter = 1;  // Лічильник для унікальних імен файлів
    public static void directorInterface(ManagerDB managers, SellerDB sellers, ProductDB products, BrandDB brands, SaleSystem salesystem, ReportingService reportingService, DataExporter dataExporter) throws SQLException {
        // This is your original main menu functionality
        DirectorMethods directorMethods = new DirectorMethods(managers, sellers,products, brands, salesystem, reportingService, dataExporter);
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
                    directorMethods.showEmployees(managers, sellers);
                    break;
                case 2:
                    directorMethods.showProducts(products);
                    break;
                case 3:
                    directorMethods.showBrands(brands);
                    break;
                case 4:
                    directorMethods.simulateSales(products,sellers,salesystem,brands);
                    break;
                case 5:
                    directorMethods.generateReports(reportingService,dataExporter);
                    break;
                case 6:
                    return;
            }
        }
    }
}
