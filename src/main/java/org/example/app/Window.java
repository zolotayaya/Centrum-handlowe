package org.example.app;
import org.example.model.Product;

import java.util.*;
import java.util.Scanner;

import static java.awt.Color.RED;
import static java.awt.Font.BOLD;
import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlAttr.InputType.RESET;

public abstract class Window {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
    private static Scanner scanner = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println(RED + "Please enter a number from " + min + " to " + max + RESET);
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println(RED + "Invalid input. Please enter an integer.." + RESET);
            }
        }
    }

    public static void pause() {
        System.out.println();
        System.out.print(BOLD + "Press Enter to continue..." + RESET);
        scanner.nextLine();
    }

    public static void printHeader(String text) {
        System.out.println(BOLD + PURPLE + "=== " + text + " ===" + RESET);
        System.out.println();
    }

    public static void printSubHeader(String text) {
        System.out.println(BOLD + CYAN + "◆ " + text + RESET);
    }

    public static void printStatus(String text) {
        System.out.println(BLUE + "➤ " + text + RESET);
    }

    public static void printSuccess(String text) {
        System.out.println(GREEN + "✓ " + text + RESET);
    }

    public static void printError(String text) {
        System.out.println(RED + "✗ " + text + RESET);
    }

    public static void printTableHeader(String[] columns) {
        System.out.print(BOLD + BLUE);
        for (String col : columns) {
            System.out.printf("│ %-15s ", col);
        }
        System.out.println("│" + RESET);
        System.out.println(BLUE + "├" + "─────────────────┼".repeat(columns.length - 1) + "─────────────────┤" + RESET);
    }

    public static void printTableRow(String[] values, String color) {
        System.out.print(color);
        for (String val : values) {
            System.out.printf("│ %-15s ", val);
        }
        System.out.println("│" + RESET);
        }
}
