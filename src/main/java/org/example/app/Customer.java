package org.example.app;

import org.example.model.Review;
import org.example.dao.ProductDB;
import org.example.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Customer extends Window{

    public static void customerInterface(ProductDB products) throws SQLException {
        while (true) {
            clearScreen();
            printHeader("Customer Portal");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "1." + Colors.RESET.get() + " View Available Products");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "2." + Colors.RESET.get() + " Purchase a Product");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "3." + Colors.RESET.get() + " Leave a Product Review");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "4." + Colors.RESET.get() + " See products Review");
            System.out.println(Colors.BOLD.get() + Colors.RED.get() + "5." + Colors.RESET.get() + " Return to Main Menu");
            System.out.print(Colors.BOLD.get() + "Select option: " + Colors.RESET.get());

            int choice = getIntInput(1, 4);

            switch (choice) {
                case 1:
                    showProducts(products);
                    break;
                case 2:
                    // purchaseProduct() method to be implemented
                    System.out.println(Colors.YELLOW.get() + "Purchase functionality coming soon!" + Colors.RESET.get());
                    pause();
                    break;
                case 3:
                    leaveReview(products);
                    System.out.println(Colors.YELLOW.get() + "Review functionality coming soon!" + Colors.RESET.get());
                    pause();
                    break;
                case 4:
                    viewProductReviews(products);
                    break;
                case 5:
                    return;
            }
        }
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

    private static void leaveReview(ProductDB product) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        clearScreen();
        printHeader("Leave Product Review");

        if (product.getProducts().isEmpty()) {
            printError("No products available for review!");
            pause();
            return;
        }

        // Показываем продукты с их ID
        System.out.println(Colors.BOLD.get() + "Available Products:" + Colors.RESET.get());
        for (int i = 0; i < product.getProducts().size(); i++) {
            Product p = product.getProducts().get(i);
            System.out.printf("%s%d.%s %s (ID: %d)\n",
                    Colors.BOLD.get(), i + 1, Colors.RESET.get(), p.getName(), p.getId());
        }

        System.out.print("\nSelect product number: ");
        int productIndex = getIntInput(1, product.getProducts().size()) - 1;
        Product selectedProduct = product.getProducts().get(productIndex);

        System.out.print("Enter rating (1-5): ");
        int rating = getIntInput(1, 5);

        System.out.print("Enter your comment: ");
        scanner.nextLine(); // Очистка буфера
        String comment = scanner.nextLine();

        // Сохраняем отзыв с ID и названием продукта
        Review.saveReview(selectedProduct.getId(), selectedProduct.getName(),
                rating, comment);

        printSuccess("Thank you for your review!");
        pause();
    }

    public static int getIntInput(int min, int max) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println(Colors.RED.get() + "Please enter a number from " + min + " to " + max + Colors.RESET.get());
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println(Colors.RED.get() + "Invalid input. Please enter an integer.." + Colors.RESET.get());
            }
        }
    }

    private static void viewProductReviews(ProductDB product) throws SQLException {
        clearScreen();
        printHeader("Product Reviews");

        if (product.getProducts().isEmpty()) {
            printError("No products available!");
            pause();
            return;
        }

        // Показываем продукты с их ID
        System.out.println(Colors.BOLD.get() + "Available Products:" + Colors.RESET.get());
        for (int i = 0; i < product.getProducts().size(); i++) {
            Product p = product.getProducts().get(i);
            System.out.printf("%s%d.%s %s (ID: %d)\n",
                    Colors.BOLD.get(), i + 1, Colors.RESET.get(), p.getName(), p.getId());
        }

        System.out.print("\nSelect product number: ");
        int productIndex = getIntInput(1, product.getProducts().size()) - 1;
        Product selectedProduct = product.getProducts().get(productIndex);

        // Получаем отзывы по ID продукта
        List<Review> reviews = Review.getReviewsByProductId(selectedProduct.getId());

        clearScreen();
        printHeader("Reviews for: " + selectedProduct.getName());

        if (reviews.isEmpty()) {
            printError("No reviews yet for this product!");
        } else {
            printTableHeader(new String[]{"Rating", "Date", "Comment"});
            for (Review review : reviews) {
                printTableRow(new String[]{
                        String.valueOf(review.getRating()),
                        review.getDate(),
                        review.getComment()
                }, Colors.CYAN.get());
            }
        }
        pause();
    }

}
