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
/**
 * Główna klasa aplikacji "Shopping Center Management System".
 * Odpowiada za inicjalizację danych, uruchomienie programu
 * oraz obsługę głównego menu użytkownika.
 */
public class ShoppingCenter extends Window{
    // === Pola pomocnicze ===
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

    /**
     * Konstruktor klasy ShoppingCenter.
     */
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
    /**
     * Główna metoda startowa aplikacji.
     * Inicjalizuje dane oraz wyświetla menu wyboru roli użytkownika.
     *
     * @throws SQLException jeśli wystąpi błąd podczas komunikacji z bazą danych
     * @throws UnsupportedEncodingException jeśli ustawienie UTF-8 się nie powiedzie
     */
    public void start() throws SQLException, UnsupportedEncodingException {
        initializeDatabase();// Inicjalizacja danych z bazy
        selectUserRole();// Wybór trybu pracy przez użytkownika
    }

    /**
     * Wyświetla główne menu, umożliwiające wybór roli użytkownika:
     * Klient, Dyrektor, Nowy pracownik, lub Wyjście z programu.
     *
     * @throws SQLException jeśli wystąpi błąd podczas pobierania danych z bazy
     */
    private void selectUserRole() throws SQLException {
        while (true) {
            clearScreen();
            printHeader("Shopping Center Management System");
            // Opcje do wyboru
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "1." + Colors.RESET.get() + " Enter as Customer");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "2." + Colors.RESET.get() + " Enter as Director");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "3." + Colors.RESET.get() + " Get a job");
            System.out.println(Colors.BOLD.get() + Colors.RED.get() + "4." + Colors.RESET.get() + " Exit System");
            System.out.print(Colors.BOLD.get() + "Select your role: " + Colors.RESET.get());

            int choice = getIntInput(1, 4);// Pobranie decyzji użytkownika

            switch (choice) {
                case 1:
                    // Przejście do interfejsu klienta
                    Customer.customerInterface(product,manager,seller,brand,saleSystem);
                    break;
                case 2:
                    // Przejście do interfejsu dyrektora
                    Director.directorInterface(manager,seller,product,brand,saleSystem,reportingService,dataExporter);
                    break;
                case 3:
                    // Rozpoczęcie procesu rekrutacji
                    if(HR_Service.takeQuizDepartment()){
                        System.out.println(HR_Service.generateEmployeers(seller,department));
                    }
                    break;
                case 4:
                    // Zakończenie programu
                    System.out.println(Colors.GREEN.get() + "Exiting system..." + Colors.RESET.get());
                    return;
            }
        }
    }

    /**
     * Inicjalizuje bazę danych: czyści stare dane i ładuje dane startowe,
     * a następnie pobiera je do pamięci aplikacji.
     *
     * @throws SQLException jeśli operacje na bazie danych się nie powiodą
     */
    public void initializeDatabase() throws SQLException {
        printStatus("Initializing database...");
        db.cleanDB(); // Czyszczenie bazy danych
        db.initializationDB(); // Inicjalizacja (np. wstawienie danych testowych)
        System.out.println("Initialized allready database");
        // Załadowanie danych do aplikacji z bazy danych
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

    /**
     * Metoda główna aplikacji – punkt wejściowy programu.
     * Ustawia kodowanie UTF-8 i uruchamia instancję aplikacji.
     *
     * @param args argumenty z linii komend (nieużywane)
     * @throws UnsupportedEncodingException jeśli wystąpi błąd ustawienia UTF-8
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out, true, "UTF-8")); //Ustawienie UTF-8 dla polskich znaków
        try {
            ShoppingCenter app = new ShoppingCenter();// Utworzenie instancji aplikacji
            app.start();// Uruchomienie aplikacji
        } catch (SQLException | UnsupportedEncodingException e) {
            // Obsługa błędów związanych z bazą danych
            System.out.println(Colors.RED.get() + "Database Error: " + e.getMessage() + Colors.RESET.get());
        }
    }
}
