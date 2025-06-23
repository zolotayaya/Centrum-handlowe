package org.example.model;

import org.example.app.Colors;
import org.example.dao.DepartmentDB;
import org.example.dao.HRServiceDB;
import org.example.dao.SellerDB;

import java.sql.SQLException;
import java.util.*;

/**
 * Klasa HR_Service odpowiada za funkcjonalności działu HR, takie jak:
 * - przeprowadzanie quizu kwalifikacyjnego dla pracowników,
 * - tworzenie nowych pracowników (sprzedawców),
 * - wyszukiwanie działów po nazwie.
 */
public class HR_Service {
    private static HRServiceDB service; // Obiekt do obsługi operacji bazy danych związanych z HR

    /**
     * Przeprowadza quiz kwalifikacyjny dla pracownika.
     * Quiz składa się z pytań z odpowiedziami TAK/NIE.
     *
     * @return true jeśli pracownik uzyskał co najmniej 5 poprawnych odpowiedzi, false w przeciwnym wypadku
     * @throws SQLException w przypadku błędu podczas inicjalizacji quizu z bazy danych
     */
    public static boolean takeQuizDepartment() throws SQLException {
        service = new HRServiceDB(); // Inicjalizacja obiektu obsługującego bazę danych
        service.initialiseQuize();   // Załadowanie pytań i odpowiedzi z bazy
        Scanner scanner = new Scanner(System.in);
        int right_Ansers = 0;

        System.out.println(Colors.BOLD.get() + Colors.GREEN.get() + "Starting Quiz " + Colors.YELLOW.get() + "( odpowiedz 'TAK' lub 'NIE')");
        // Przechodzenie przez wszystkie pytania quizu
        for (int i = 0; i < service.getQuestions().size(); i++) {
            System.out.println(Colors.CYAN.get() + service.getQuestions().get(i));
            String your_Answer = scanner.nextLine();
            if (your_Answer.toLowerCase().equals(service.getAnswers().get(i))) {
                right_Ansers++;
            }
        }
        // Sprawdzenie, czy liczba poprawnych odpowiedzi jest wystarczająca
        if (right_Ansers >= 5) {
            System.out.println("Quiz finished. You have " + right_Ansers + " correct answers.");
            return true;
        } else {
            System.out.println("Unfortunately you have only " + right_Ansers + " correct answers. That is too low. Try again next time :)");
            return false;
        }
    }

    /**
     * Tworzy nowego sprzedawcę na podstawie danych wprowadzonych przez użytkownika.
     * Pracownik jest zapisywany w bazie danych i aktualizowany w obiekcie SellerDB.
     *
     * @param sellers    obiekt Seller do zarządzania sprzedawcami
     * @param departments obiekt Department zawierający dostępne działy
     * @return komunikat potwierdzający utworzenie pracownika
     * @throws SQLException w przypadku błędu podczas zapisu w bazie danych
     */
    public static String generateEmployeers(SellerDB sellers, DepartmentDB departments) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter employeer name: ");
        String name = scanner.nextLine();

        System.out.println("Enter your experience years");
        int years = scanner.nextInt();
        scanner.nextLine(); // czyści bufor po nextInt()

        boolean found = true;
        String department = null;

        // Pętla wyboru działu przez użytkownika - sprawdza poprawność nazwy działu
        while (found) {
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
        // Utworzenie pracownika w bazie danych
        int id_employee = service.create_Employee_In_DB(name, years, department);

        // Aktualizacja danych sprzedawcy w bazie oraz rejestracja w SellerDB
        SellerDB sellers_db = SellerDB.getInstance();
        Seller seller = new Seller(id_employee, name, getDep(department, departments), 0, 10, 0, 0, years);
        sellers_db.updateSeller(seller);

        return "Employee " + name + " has been created";
    }

    /**
     * Wyszukuje obiekt Department na podstawie jego nazwy.
     *
     * @param department_name nazwa działu do znalezienia
     * @param departments     obiekt Department zawierający listę działów
     * @return znaleziony obiekt Department lub null jeśli nie znaleziono
     * @throws SQLException w przypadku błędu podczas pobierania działów z bazy danych
     */
    public static Department getDep(String department_name, DepartmentDB departments) throws SQLException {
        for (Department department : departments.getDepartments()) {
            if (department.getName().equals(department_name)) {
                return department;
            }
        }
        return null;
    }
}
