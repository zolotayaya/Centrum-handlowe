package org.example.app;

import org.example.dao.*;
import org.example.model.*;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Customer extends Window{

    public static void customerInterface(ProductDB products, ManagerDB managers, SellerDB sellers, BrandDB brands, SaleSystem salesystem) throws SQLException {
        while (true) {
            clearScreen();
            printHeader("Customer Portal");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "1." + Colors.RESET.get() + " View Available Products");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "2." + Colors.RESET.get() + " Purchase a Product");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "3." + Colors.RESET.get() + " Leave a Product Review");
            System.out.println(Colors.BOLD.get() + Colors.BLUE.get() + "4." + Colors.RESET.get() + " See products Review");
            System.out.println(Colors.BOLD.get() + Colors.RED.get() + "5." + Colors.RESET.get() + " Return to Main Menu");
            System.out.print(Colors.BOLD.get() + "Select option: " + Colors.RESET.get());

            int choice = getIntInput(1, 5);

            switch (choice) {
                case 1:
                    showProducts(products);
                    break;
                case 2:
                    purchaseProduct(products,sellers,salesystem,brands);
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
    private static void purchaseProduct(ProductDB products, SellerDB sellers, SaleSystem saleSystem, BrandDB brands) throws SQLException {
        clearScreen();
        printHeader("Manual Purchase");

        List<Product> productList = products.getProducts();

        System.out.println("Available Products:");
        for (int i = 0; i < productList.size(); i++) {
            Product p = productList.get(i);
            System.out.printf("%d. %s (Quantity: %d, Price: %.2f)\n", i + 1, p.getName(), p.getQuantity(), p.getPrice());
        }

        System.out.print("Select product index: ");
        int productIndex = getIntInput(1, productList.size()) - 1;
        Product selectedProduct = productList.get(productIndex);

        Brand brand = selectedProduct.getBrand();
        if (brand == null) {
            System.out.println("⚠ У продукта не указан бренд!");
            pause();
            return;
        }

        List<Seller> experts = new ArrayList<>(brand.getExperts());
        if (experts.isEmpty()) {
            System.out.println("⚠ Нет продавцов-экспертов для бренда: " + brand.getName());
            pause();
            return;
        }

        // Выбираем случайного подходящего продавца
        Seller selectedSeller = null;
        Collections.shuffle(experts); // Перемешиваем список
        for (Seller s : experts) {
            if (s.canSellNow()) {
                selectedSeller = s;
                break;
            }
        }

        if (selectedSeller == null) {
            System.out.println("⚠ Все продавцы достигли лимита продаж на текущий час. Попробуйте позже.");
            pause();
            return;
        }

        System.out.printf("💼 Selected Expert: %s (Rating: %.1f)\n", selectedSeller.getName(), selectedSeller.getRating());

        System.out.print("Enter quantity to purchase: ");
        int quantity = getIntInput(1, selectedProduct.getQuantity());

        System.out.print("Enter buyer ID: ");
        int buyerID = getIntInput(1, 999999);

        saleSystem.processPurchase(selectedProduct, selectedSeller, quantity, buyerID);
        products.updateQuantityInDB(selectedProduct, selectedProduct.getQuantity());

        // Логируем продажу у продавца
        selectedSeller.recordSale(); // ➤ добавь этот метод в Seller

        SellerDB.updateSellerStats(selectedSeller);

        Manager manager = selectedSeller.getDepartment().getManager();
        if (manager != null) {
            ManagerDB.updateManagerIncome(manager);
        }

        Boss boss = BossDB.getBoss();
        if (boss != null) {
            new BossDB().updateIncomeInDatabase();
        }

        System.out.println("✅ Purchase completed successfully!");
        System.out.print("Please rate the seller from 1 to 5: ");
        int rating = getIntInput(1, 5);

// ➤ Просто вызываем метод
        selectedSeller.addRating(rating);

// ➤ Обновляем в базе
        SellerDB.updateSellerRating(selectedSeller);

        System.out.printf("✅ Seller's new average rating: %.2f\n", selectedSeller.getRating());

        pause();
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

        //Overwatch of products by ID
        System.out.println(Colors.BOLD.get() + "Available Products:" + Colors.RESET.get());
        for (int i = 0; i < product.getProducts().size(); i++) {
            Product p = product.getProducts().get(i);
            System.out.printf("%s%d.%s %s (ID: %d)\n",
                    Colors.BOLD.get(), i + 1, Colors.RESET.get(), p.getName(), p.getId());
        }
        System.out.print("\nSelect product number: ");
        System.out.println("Size of products: " + product.getProducts().size());
        int productIndex = getIntInput(1, product.getProducts().size());
        Product selectedProduct = product.getProducts().get(productIndex-1);
        System.out.print("Enter rating (1-5): ");
        int rating = getIntInput(1, 5);
        System.out.print("Enter your comment: ");
        String comment = scanner.nextLine();
        Review.saveReview(productIndex,selectedProduct.getName(),rating,comment);
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
            if (p.getId() == 0) {
                System.out.println("ID = " + 0);
            }
        }

        System.out.print("\nSelect product number: ");
        int productIndex = getIntInput(1, product.getProducts().size()) - 1;
        Product selectedProduct = product.getProducts().get(productIndex);
        ReviewDB reviewDB = new ReviewDB();
        List<Review> reviews = reviewDB.getReviewsByProductId(selectedProduct.getId());

        clearScreen();
        printHeader("Reviews for: " + selectedProduct.getName());

        if (reviews.isEmpty()) {
            printError("No reviews yet for this product!");
        } else {
            int maxCommentLength = reviews.stream()
                    .mapToInt(review -> review.getComment().length())
                    .max()
                    .orElse(20);

// Обмеження максимальної довжини коментаря
            maxCommentLength = Math.min(maxCommentLength, 50); // Не більше 50 символів

// Форматування таблиці
            String separator = Colors.CYAN.get() + "+" + "-".repeat(9) + "+" + "-".repeat(22) + "+" +
                    "-".repeat(maxCommentLength + 2) + "+" + Colors.RESET.get();
            String headerFormat = Colors.CYAN.get() + "|" + Colors.PURPLE.get() + Colors.BOLD.get() + " %-7s " + Colors.CYAN.get() + "|" +
                    Colors.PURPLE.get() + Colors.BOLD.get() + " %-20s " + Colors.CYAN.get() + "|" +
                    Colors.PURPLE.get() + Colors.BOLD.get() + " %-" + maxCommentLength + "s " + Colors.CYAN.get() + "|" + Colors.RESET.get() + "\n";
            String rowFormat = Colors.CYAN.get() + "| " + Colors.YELLOW.get() + "%-7d " + Colors.CYAN.get() + "| " + Colors.GREEN.get() + "%-20s " +
                    Colors.CYAN.get() + "| " + Colors.RESET.get() + "%-" + maxCommentLength + "s " + Colors.CYAN.get() + "|" + Colors.RESET.get() + "\n";

// Вивід таблиці
            System.out.println(separator);
            System.out.printf(headerFormat, "Rating", "Date", "Comment");
            System.out.println(separator);

            for (Review review : reviews) {
                String formattedComment = review.getComment().length() > maxCommentLength ?
                        review.getComment().substring(0, maxCommentLength - 3) + "..." :
                        review.getComment();

                System.out.printf(rowFormat,
                        review.getRating(),
                        review.getFormattedDate(),
                        formattedComment);
            }
            System.out.println(separator);
            pause();
        }
    }
}
