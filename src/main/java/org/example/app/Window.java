package org.example.app;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Abstrakcyjna klasa bazowa dla tekstowego interfejsu użytkownika.
 * Zawiera zestaw pomocniczych metod do obsługi konsoli (wyświetlanie komunikatów,
 * tabel, nagłówków, wczytywanie danych itd.).
 */
public abstract class Window {
    // Stałe ANSI do kolorowania tekstu w konsoli
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
    private static Scanner scanner = new Scanner(System.in);// Wspólny skaner do wczytywania danych od użytkownika
    private static boolean testMode = false;// Flaga trybu testowego – gdy aktywna, pomija niektóre interakcje
    /**
     * Czyści ekran terminala
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    /**
     * Ustawia niestandardowy obiekt {@link Scanner}, używany do wczytywania danych od użytkownika.
     *
     * @param scanner1 nowy obiekt Scanner do użycia
     */
    public static void setScanner(Scanner scanner1) {
        scanner = scanner1;
    }
    /**
     * Wczytuje liczbę całkowitą od użytkownika mieszczącą się w zakresie [min, max].
     * Obsługuje nieprawidłowe wpisy i komunikuje błędy.
     *
     * @param min minimalna dozwolona wartość
     * @param max maksymalna dozwolona wartość
     * @return poprawna liczba całkowita w podanym zakresie
     */
    public static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();// czyści bufor po enterze
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println(RED + "Please enter a number from " + min + " to " + max + RESET);
            } catch (Exception  e) {
                scanner.nextLine();// czyści błędny wpis
                System.out.println(RED + "Invalid input. Please enter an integer.." + RESET);
            }
        }
    }

    /**
     * Zatrzymuje program do momentu naciśnięcia klawisza Enter.
     * Pomijane, gdy aktywny jest tryb testowy.
     */
    public static void pause() {
        if (!testMode) {
            System.out.println();
            System.out.print(BOLD + "Press Enter to continue..." + RESET);
            scanner.nextLine();
        }
    }
    /**
     * Ustawia tryb testowy działania klasy.
     *
     * @param mode true jeśli testowy, false w przeciwnym razie
     */
    public static void setTestMode(boolean mode) {
        testMode = mode;
    }

    /**
     * Wyświetla nagłówek sekcji w kolorze fioletowym i wytłuszczoną czcionką.
     *
     * @param text tekst nagłówka
     */
    public static void printHeader(String text) {
        System.out.println(BOLD + PURPLE + "=== " + text + " ===" + RESET);
        System.out.println();
    }
    /**
     * Wyświetla podtytuł (nagłówek niższego poziomu) w kolorze cyjanowym.
     *
     * @param text tekst podtytułu
     */
    public static void printSubHeader(String text) {
        System.out.println(BOLD + CYAN + "◆ " + text + RESET);
    }

    /**
     * Wyświetla komunikat informacyjny w kolorze niebieskim z ikoną.
     *
     * @param text treść komunikatu
     */
    public static void printStatus(String text) {
        System.out.println(BLUE + "➤ " + text + RESET);
    }

    /**
     * Wyświetla komunikat sukcesu w kolorze zielonym z ikoną ✓.
     *
     * @param text treść komunikatu
     */
    public static void printSuccess(String text) {
        System.out.println(GREEN + "✓ " + text + RESET);
    }

    /**
     * Wyświetla komunikat błędu w kolorze czerwonym z ikoną ✗.
     *
     * @param text treść błędu
     */
    public static void printError(String text) {
        System.out.println(RED + "✗ " + text + RESET);
    }

    /**
     * Wyświetla nagłówki kolumn tabeli w konsoli w formacie tabelarycznym.
     *
     * @param columns tablica z nazwami kolumn
     */
    public static void printTableHeader(String[] columns) {
        System.out.print(BOLD + BLUE);
        for (String col : columns) {
            System.out.printf("│ %-25s ", col);
        }
        System.out.println("│" + RESET);
        System.out.println(BLUE + "├" + "───────────────────────────┼".repeat(columns.length - 1) + "───────────────────────────┤" + RESET);
    }
    /**
     * Wyświetla pojedynczy wiersz danych tabeli z określonym kolorem tekstu.
     *
     * @param values tablica wartości do wyświetlenia
     * @param color  kod ANSI koloru (np. BLUE, GREEN) lub pusty string
     */
    public static void printTableRow(String[] values, String color) {
        System.out.print(color);
        for (String val : values) {
            System.out.printf("│ %-25s ", val);
        }
        System.out.println("│" + RESET);
        }
}
