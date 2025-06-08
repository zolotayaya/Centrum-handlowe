package org.example.database;
import org.example.model.Department;
import org.example.PurchaseRecord;
import org.example.model.*;

import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;
public class Database {
    private List<Seller> sellers;
    private List<Manager> managers;
    private List<Brand> brands;
    private Boss boss;
    private static List<Department> department;
    private List<Product> products;
    private List<PurchaseRecord> sales;
    private static Connection conection;
    private static Database instance;

    public Database() {
        connection();  // połączenie wywołujemy raz podczas tworzenia obiektu
        // inicjalizacja list
        sellers = new ArrayList<>();
        managers = new ArrayList<>();
        brands = new ArrayList<>();
        department = new ArrayList<>();
        products = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public static Database getInstance() { //Singleton
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void connection() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "datax";
        try {
            Class.forName("org.postgresql.Driver");
            this.conection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection sequscees");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
 public void initializationDB() throws SQLException {
        String sql_create_Sellers = "CREATE TABLE Sellers (\n" +
                "    id INT PRIMARY KEY,\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "    department VARCHAR(50) NOT NULL,\n" +
                "    salary INT DEFAULT 0,\n" +
                "    experience_years INT,\n" +
                "    rating NUMERIC(3, 1),\n" +
                "    commission INT,\n" +
                "    salescount INT DEFAULT 0\n" +
                ")";
        String sql_create_Departments = "CREATE TABLE Departments (\n" +
                "    id INT PRIMARY KEY,\n" +
                "    name VARCHAR(50) NOT NULL\n" +
                ");\n";
        String sql_create_Products = "CREATE TABLE Products (\n" +
                "    id INT PRIMARY KEY,\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "    price DECIMAL(10, 2) NOT NULL,\n" +
                "    quantity INT DEFAULT 1000,\n" +
                "    description TEXT,\n" +
                "    brand VARCHAR(50) NOT NULL\n" +
                ");\n";

        String sql_create_Managers = "CREATE TABLE Managers (\n" +
                "    id INT PRIMARY KEY,\n" +
                "    name VARCHAR(50) NOT NULL,\n" +
                "    department VARCHAR(50) NOT NULL,\n" +
                "    income DECIMAL(10, 2) DEFAULT 0.00,\n" +
                "    commission INT,\n" +
                "    experience INT\n" +
                ");";

        String sql_create_Brands = "CREATE TABLE Brands (\n" +
                "    id INT PRIMARY KEY,\n" +
                "    name VARCHAR(50) NOT NULL,\n" +
                "    department VARCHAR(50) NOT NULL\n" +
                ");";

        String sql_create_Boss = "CREATE TABLE Boss (\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    name VARCHAR(50) NOT NULL,\n" +
                "    income DECIMAL(10, 2) DEFAULT 0.00\n" +
                ");";

        String sql_create_Reviews = "CREATE TABLE Reviews(\n" +
                "product_id INTEGER,\n" +
                "product_name VARCHAR(100),\n" +
                "rating DECIMAL(10, 1) DEFAULT 0.00\n" +
                ");";

        String sql_insert_Sellers  = "INSERT INTO Sellers (id, name, department, salary, experience_years, rating, commission, salescount)\n" +
                "VALUES\n" +
                "    (35, 'Dominik Baran', 'Shoes', 0, 5, 4.3, 10, 0),\n" +
                "    (37, 'Emil Tomaszewski', 'Cosmetics', 0, 8, 4.2, 19, 0),\n" +
                "    (9, 'Marcin Woźniak', 'Shoes', 0, 1, 4.0, 20, 0),\n" +
                "    (24, 'Wojciech Pawłowski', 'Garden Tools', 0, 3, 4.4, 11, 0),\n" +
                "    (2, 'Piotr Nowak', 'Electronics', 0, 5, 4.0, 15, 0),\n" +
                "    (32, 'Konrad Sikorski', 'Clothes', 0, 3, 4.4, 19, 0),\n" +
                "    (33, 'Igor Ostrowski', 'Shoes', 0, 4, 3.9, 20, 0),\n" +
                "    (40, 'Daniel Kalinowski', 'Cosmetics', 0, 2, 4.0, 20, 0),\n" +
                "    (39, 'Aleksander Wilk', 'Cosmetics', 0, 5, 3.9, 13, 0),\n" +
                "    (31, 'Przemysław Rutkowski', 'Clothes', 0, 9, 4.2, 19, 0),\n" +
                "    (20, 'Artur Wieczorek', 'Garden Tools', 0, 7, 4.2, 13, 0),\n" +
                "    (3, 'Andrzej Wiśniewski', 'Electronics', 0, 9, 4.7, 19, 0),\n" +
                "    (28, 'Dawid Krupa', 'Electronics', 0, 2, 4.5, 10, 0),\n" +
                "    (38, 'Patryk Szczepański', 'Cosmetics', 0, 6, 4.3, 19, 0),\n" +
                "    (5, 'Paweł Kamiński', 'Clothes', 0, 2, 3.9, 17, 0),\n" +
                "    (34, 'Mariusz Sadowski', 'Shoes', 0, 7, 4.5, 20, 0),\n" +
                "    (14, 'Szymon Krawczyk', 'Cosmetics', 0, 1, 4.1, 13, 0),\n" +
                "    (27, 'Sebastian Lis', 'Electronics', 0, 9, 4.1, 20, 0),\n" +
                "    (1, 'Jan Kowalski', 'Electronics', 0, 5, 4.5, 16, 0),\n" +
                "    (16, 'Rafał Grabowski', 'Cosmetics', 0, 2, 4.5, 17, 0),\n" +
                "    (26, 'Hubert Zając', 'Electronics', 0, 7, 4.2, 12, 0),\n" +
                "    (36, 'Karol Duda', 'Shoes', 0, 2, 4.1, 17, 0),\n" +
                "    (15, 'Damian Piotrowski', 'Cosmetics', 0, 5, 3.6, 18, 0),\n" +
                "    (13, 'Jakub Mazur', 'Cosmetics', 0, 7, 4.4, 10, 0),\n" +
                "    (7, 'Michał Zieliński', 'Clothes', 0, 3, 4.3, 14, 0),\n" +
                "    (8, 'Grzegorz Szymański', 'Clothes', 0, 3, 3.8, 13, 0),\n" +
                "    (10, 'Adam Dąbrowski', 'Shoes', 0, 1, 4.6, 13, 0),\n" +
                "    (11, 'Łukasz Kozłowski', 'Shoes', 0, 9, 4.8, 14, 0),\n" +
                "    (4, 'Tomasz Wójcik', 'Electronics', 0, 7, 4.2, 12, 0),\n" +
                "    (29, 'Kacper Walczak', 'Clothes', 0, 1, 4.0, 16, 0),\n" +
                "    (12, 'Mateusz Jankowski', 'Shoes', 0, 7, 3.7, 14, 0),\n" +
                "    (23, 'Maciej Górski', 'Garden Tools', 0, 2, 3.8, 16, 0),\n" +
                "    (17, 'Dariusz Pawlak', 'Garden Tools', 0, 6, 4.0, 15, 0),\n" +
                "    (6, 'Krzysztof Lewandowski', 'Clothes', 0, 6, 4.1, 16, 0),\n" +
                "    (18, 'Patryk Michalski', 'Garden Tools', 0, 7, 4.3, 16, 0),\n" +
                "    (19, 'Adrian Król', 'Garden Tools', 0, 8, 3.9, 15, 0),\n" +
                "    (30, 'Filip Wesołowski', 'Clothes', 0, 1, 3.7, 16, 0),\n" +
                "    (25, 'Radosław Sikora', 'Electronics', 0, 2, 4.6, 13, 0),\n" +
                "    (21, 'Kamil Nowicki', 'Garden Tools', 0, 6, 4.1, 20, 0),\n" +
                "    (22, 'Bartosz Czarnecki', 'Garden Tools', 0, 7, 4.3, 16, 0);";


        String sql_insert_Departments = "INSERT INTO Departments (id, name)\n" +
                "VALUES\n" +
                "    (1, 'Electronics'),\n" +
                "    (2, 'Clothes'),\n" +
                "    (3, 'Shoes'),\n" +
                "    (4, 'Cosmetics'),\n" +
                "    (5, 'Garden Tools');\n";

        String sql_insert_Products = "INSERT INTO Products (id, name, price, quantity, description, brand) VALUES\n" +
                "    (1, 'iPhone 11', 6500.00, 1000, 'Telephone 256 GB', 'Apple'),\n" +
                "    (2, 'iPhone 12', 7200.00, 1000, 'Telephone 128 GB', 'Apple'),\n" +
                "    (3, 'iPad Air', 3800.00, 1000, 'Tablet with M1 chip', 'Apple'),\n" +
                "    (4, 'MacBook Air', 9500.00, 1000, 'Laptop M2, 13 inch', 'Apple'),\n" +
                "    (5, 'AirPods Pro', 1100.00, 1000, 'Wireless earphones', 'Apple'),\n" +
                "    (6, 'Apple Watch SE', 1200.00, 1000, 'Smartwatch 40mm', 'Apple'),\n" +
                "    (7, 'Galaxy S21', 5400.00, 1000, 'Smartphone 128GB', 'Samsung'),\n" +
                "    (8, 'Galaxy Tab S7', 3600.00, 1000, 'Android tablet', 'Samsung'),\n" +
                "    (9, 'Galaxy Watch 4', 1000.00, 1000, 'Smartwatch', 'Samsung'),\n" +
                "    (10, 'Galaxy Buds', 650.00, 1000, 'Wireless earbuds', 'Samsung'),\n" +
                "    (11, 'Samsung TV 55\"', 3800.00, 1000, '4K UHD TV', 'Samsung'),\n" +
                "    (12, 'Samsung SSD 1TB', 450.00, 1000, 'Solid State Drive', 'Samsung'),\n" +
                "    (13, 'Sony WH-1000XM4', 1300.00, 1000, 'Noise cancelling headphones', 'Sony'),\n" +
                "    (14, 'Sony Xperia 5', 4900.00, 1000, 'Smartphone 128GB', 'Sony'),\n" +
                "    (15, 'Sony Bravia 50\"', 4200.00, 1000, '4K LED TV', 'Sony'),\n" +
                "    (16, 'Sony PlayStation 5', 5800.00, 1000, 'Gaming console', 'Sony'),\n" +
                "    (17, 'Sony Soundbar', 1500.00, 1000, 'Home audio system', 'Sony'),\n" +
                "    (18, 'Sony Camera A6000', 3600.00, 1000, 'Mirrorless camera', 'Sony'),\n" +
                "    (19, 'LG OLED TV', 6200.00, 1000, 'OLED Smart TV 55\"', 'LG'),\n" +
                "    (20, 'LG Washing Machine', 3700.00, 1000, '7kg front load', 'LG'),\n" +
                "    (21, 'LG Fridge 300L', 4900.00, 1000, 'Smart inverter', 'LG'),\n" +
                "    (22, 'LG Monitor 27\"', 1000.00, 1000, 'Full HD monitor', 'LG'),\n" +
                "    (23, 'LG Sound Bar', 1100.00, 1000, 'Home theater soundbar', 'LG'),\n" +
                "    (24, 'LG Microwave', 850.00, 1000, 'Digital microwave oven', 'LG'),\n" +
                "    (25, 'Zara Shirt', 120.00, 1000, 'Cotton shirt for men', 'Zara'),\n" +
                "    (26, 'Zara Dress', 250.00, 1000, 'Elegant dress for women', 'Zara'),\n" +
                "    (27, 'Zara Jacket', 500.00, 1000, 'Winter jacket', 'Zara'),\n" +
                "    (28, 'Zara Jeans', 200.00, 1000, 'Denim jeans', 'Zara'),\n" +
                "    (29, 'Zara T-shirt', 90.00, 1000, 'Basic cotton T-shirt', 'Zara'),\n" +
                "    (30, 'Zara Skirt', 180.00, 1000, 'Slim skirt', 'Zara'),\n" +
                "    (31, 'H&M Blouse', 100.00, 1000, 'Women blouse', 'H&M'),\n" +
                "    (32, 'H&M Hoodie', 210.00, 1000, 'Unisex hoodie', 'H&M'),\n" +
                "    (33, 'H&M Pants', 190.00, 1000, 'Slim fit pants', 'H&M'),\n" +
                "    (34, 'H&M Jacket', 400.00, 1000, 'Mens bomber jacket', 'H&M'),\n" +
                "    (35, 'H&M Sneakers', 250.00, 1000, 'Casual shoes', 'H&M'),\n" +
                "    (36, 'H&M Hat', 70.00, 1000, 'Winter knit hat', 'H&M'),\n" +
                "    (37, 'Nike Air Max', 600.00, 1000, 'Sport sneakers', 'Nike'),\n" +
                "    (38, 'Nike Hoodie', 300.00, 1000, 'Mens hoodie', 'Nike'),\n" +
                "    (39, 'Nike Shorts', 150.00, 1000, 'Training shorts', 'Nike'),\n" +
                "    (40, 'Nike Cap', 90.00, 1000, 'Sports cap', 'Nike'),\n" +
                "    (41, 'Nike Sports Bra', 180.00, 1000, 'Womens fitness wear', 'Nike'),\n" +
                "    (42, 'Nike Socks 3-pack', 60.00, 1000, 'Cotton socks', 'Nike'),\n" +
                "    (43, 'Adidas Ultraboost', 700.00, 1000, 'Running shoes', 'Adidas'),\n" +
                "    (44, 'Adidas Track Jacket', 400.00, 1000, 'Classic style', 'Adidas'),\n" +
                "    (45, 'Adidas Training Pants', 350.00, 1000, 'Polyester pants', 'Adidas'),\n" +
                "    (46, 'Adidas T-shirt', 120.00, 1000, 'Climalite shirt', 'Adidas'),\n" +
                "    (47, 'Adidas Bag', 180.00, 1000, 'Small backpack', 'Adidas'),\n" +
                "    (48, 'Adidas Hoodie', 300.00, 1000, 'Winter hoodie', 'Adidas'),\n" +
                "    (49, 'Clarks Formal Shoes', 430.00, 1000, 'Leather office shoes', 'Clarks'),\n" +
                "    (50, 'Clarks Sandals', 280.00, 1000, 'Summer sandals', 'Clarks'),\n" +
                "    (51, 'Clarks Sneakers', 370.00, 1000, 'Sporty casual shoes', 'Clarks'),\n" +
                "    (52, 'Clarks Boots', 600.00, 1000, 'Winter boots', 'Clarks'),\n" +
                "    (53, 'Clarks Loafers', 410.00, 1000, 'Casual slip-ons', 'Clarks'),\n" +
                "    (54, 'Clarks Derby', 450.00, 1000, 'Classic men’s shoes', 'Clarks'),\n" +
                "    (55, 'Ecco Sneakers', 480.00, 1000, 'Comfort sneakers', 'Ecco'),\n" +
                "    (56, 'Ecco Boots', 650.00, 1000, 'Waterproof boots', 'Ecco'),\n" +
                "    (57, 'Ecco Sandals', 310.00, 1000, 'Leather sandals', 'Ecco'),\n" +
                "    (58, 'Ecco Formal Shoes', 540.00, 1000, 'Office shoes', 'Ecco'),\n" +
                "    (59, 'Ecco Golf Shoes', 720.00, 1000, 'Golf sport shoes', 'Ecco'),\n" +
                "    (60, 'Ecco Walking Shoes', 520.00, 1000, 'Long walk shoes', 'Ecco'),\n" +
                "    (61, 'NB 574 Classic', 480.00, 1000, 'Retro running shoes', 'New Balance'),\n" +
                "    (62, 'NB Fresh Foam', 560.00, 1000, 'Cushion running shoes', 'New Balance'),\n" +
                "    (63, 'NB FuelCell', 530.00, 1000, 'High-performance sneakers', 'New Balance'),\n" +
                "    (64, 'NB Hoodie', 290.00, 1000, 'Comfort training hoodie', 'New Balance'),\n" +
                "    (65, 'NB Shorts', 160.00, 1000, 'Athletic shorts', 'New Balance'),\n" +
                "    (66, 'NB Socks Pack', 70.00, 1000, 'Performance socks', 'New Balance'),\n" +
                "    (67, 'Timberland Boots', 750.00, 1000, 'Classic leather boots', 'Timberland'),\n" +
                "    (68, 'Timberland Loafers', 580.00, 1000, 'Suede slip-ons', 'Timberland'),\n" +
                "    (69, 'Timberland Jacket', 950.00, 1000, 'Waterproof outdoor jacket', 'Timberland'),\n" +
                "    (70, 'Timberland Belt', 200.00, 1000, 'Leather belt', 'Timberland'),\n" +
                "    (71, 'Timberland Cap', 130.00, 1000, 'Casual cap', 'Timberland'),\n" +
                "    (72, 'Timberland Shirt', 270.00, 1000, 'Flannel outdoor shirt', 'Timberland'),\n" +
                "    (73, 'L’Oreal Shampoo', 40.00, 1000, 'Hair repair shampoo', 'LOreal'),\n" +
                "    (74, 'L’Oreal Conditioner', 45.00, 1000, 'Moisturizing conditioner', 'LOreal'),\n" +
                "    (75, 'L’Oreal Face Cream', 80.00, 1000, 'Anti-aging cream', 'LOreal'),\n" +
                "    (76, 'L’Oreal Mascara', 65.00, 1000, 'Waterproof mascara', 'LOreal'),\n" +
                "    (77, 'L’Oreal Foundation', 90.00, 1000, '24H matte foundation', 'LOreal'),\n" +
                "    (78, 'L’Oreal Lipstick', 50.00, 1000, 'Matte red lipstick', 'LOreal'),\n" +
                "    (79, 'Maybelline Mascara', 55.00, 1000, 'Volumizing mascara', 'Maybelline'),\n" +
                "    (80, 'Maybelline Lipstick', 48.00, 1000, 'Liquid matte lipstick', 'Maybelline'),\n" +
                "    (81, 'Maybelline Eyeliner', 30.00, 1000, 'Waterproof eyeliner', 'Maybelline'),\n" +
                "    (82, 'Maybelline BB Cream', 60.00, 1000, 'Light skin tone', 'Maybelline'),\n" +
                "    (83, 'Maybelline Foundation', 75.00, 1000, 'Fit Me Matte+Poreless', 'Maybelline'),\n" +
                "    (84, 'Maybelline Blush', 40.00, 1000, 'Natural cheek color', 'Maybelline'),\n" +
                "    (85, 'Nivea Cream', 30.00, 1000, 'Universal moisturizing cream', 'Nivea'),\n" +
                "    (86, 'Nivea Men Lotion', 45.00, 1000, 'After shave care', 'Nivea'),\n" +
                "    (87, 'Nivea Face Wash', 50.00, 1000, 'Gentle cleansing gel', 'Nivea'),\n" +
                "    (88, 'Nivea Deo Spray', 25.00, 1000, '48H fresh deodorant', 'Nivea'),\n" +
                "    (89, 'Nivea Lip Balm', 20.00, 1000, 'Cherry shine balm', 'Nivea'),\n" +
                "    (90, 'Nivea Body Lotion', 55.00, 1000, 'Nourishing cocoa lotion', 'Nivea'),\n" +
                "    (91, 'Avon Perfume', 95.00, 1000, 'Women fragrance', 'Avon'),\n" +
                "    (92, 'Avon Lip Gloss', 35.00, 1000, 'Shiny lip gloss', 'Avon'),\n" +
                "    (93, 'Avon Night Cream', 60.00, 1000, 'Anti-wrinkle formula', 'Avon'),\n" +
                "    (94, 'Avon Mascara', 50.00, 1000, 'Thickening mascara', 'Avon'),\n" +
                "    (95, 'Avon Hair Serum', 80.00, 1000, 'Silky hair finish', 'Avon'),\n" +
                "    (96, 'Avon Hand Cream', 25.00, 1000, 'Moisturizing care', 'Avon'),\n" +
                "    (97, 'Gardena Hose 20m', 220.00, 1000, 'Flexible garden hose', 'Gardena'),\n" +
                "    (98, 'Gardena Sprinkler', 180.00, 1000, 'Adjustable water sprinkler', 'Gardena'),\n" +
                "    (99, 'Gardena Shears', 150.00, 1000, 'Precision pruning shears', 'Gardena'),\n" +
                "    (100, 'Gardena Gloves', 60.00, 1000, 'Gardening gloves', 'Gardena'),\n" +
                "    (101, 'Gardena Water Timer', 320.00, 1000, 'Automatic irrigation timer', 'Gardena'),\n" +
                "    (102, 'Gardena Rake', 110.00, 1000, 'Metal garden rake', 'Gardena'),\n" +
                "    (103, 'Bosch Hedge Trimmer', 680.00, 1000, 'Electric garden trimmer', 'Bosch Garden'),\n" +
                "    (104, 'Bosch Lawnmower', 1400.00, 1000, 'Rotary electric mower', 'Bosch Garden'),\n" +
                "    (105, 'Bosch Blower', 550.00, 1000, 'Garden leaf blower', 'Bosch Garden'),\n" +
                "    (106, 'Bosch Drill Set', 470.00, 1000, 'Cordless drill for garden tools', 'Bosch Garden'),\n" +
                "    (107, 'Bosch Chainsaw', 1300.00, 1000, 'Electric chain saw', 'Bosch Garden'),\n" +
                "    (108, 'Bosch Sprayer', 390.00, 1000, 'Pressure sprayer', 'Bosch Garden'),\n" +
                "    (109, 'Stihl Chainsaw', 1450.00, 1000, 'Gas-powered chainsaw', 'Stihl'),\n" +
                "    (110, 'Stihl Trimmer', 780.00, 1000, 'Grass edge trimmer', 'Stihl'),\n" +
                "    (111, 'Stihl Helmet Set', 320.00, 1000, 'Safety helmet with shield', 'Stihl'),\n" +
                "    (112, 'Stihl Fuel Can', 95.00, 1000, 'Fuel container', 'Stihl'),\n" +
                "    (113, 'Stihl Oil', 70.00, 1000, '2-stroke engine oil', 'Stihl'),\n" +
                "    (114, 'Stihl Gloves', 85.00, 1000, 'Protective work gloves', 'Stihl'),\n" +
                "    (115, 'Fiskars Axe X10', 390.00, 1000, 'Multipurpose axe', 'Fiskars'),\n" +
                "    (116, 'Fiskars Pruner', 160.00, 1000, 'Bypass pruner', 'Fiskars'),\n" +
                "    (117, 'Fiskars Trowel', 70.00, 1000, 'Garden trowel tool', 'Fiskars'),\n" +
                "    (118, 'Fiskars Rake', 120.00, 1000, 'Wide leaf rake', 'Fiskars'),\n" +
                "    (119, 'Fiskars Lopper', 240.00, 1000, 'Tree branch cutter', 'Fiskars'),\n" +
                "    (120, 'Fiskars Gloves', 55.00, 1000, 'Grip gloves', 'Fiskars');";

        String sql_insert_Managers =  "INSERT INTO Managers (id, name, department, income, commission, experience) VALUES\n" +
                "    (1, 'Monika', 'Electronics', 0.00, 1, 6),\n" +
                "    (5, 'Piotr', 'Garden Tools', 0.00, 2, 5),\n" +
                "    (4, 'Jan', 'Cosmetics', 0.00, 3, 4),\n" +
                "    (2, 'Adam', 'Clothes', 0.00, 2, 7),\n" +
                "    (3, 'Katarzyna', 'Shoes', 0.00, 1, 3);";

        String sql_insert_Brands  = "INSERT INTO Brands (id, name, department) VALUES\n" +
                "    (1, 'Apple', 'Electronics'),\n" +
                "    (2, 'Samsung', 'Electronics'),\n" +
                "    (3, 'Sony', 'Electronics'),\n" +
                "    (4, 'LG', 'Electronics'),\n" +
                "    (5, 'Zara', 'Clothes'),\n" +
                "    (6, 'H&M', 'Clothes'),\n" +
                "    (7, 'Nike', 'Clothes'),\n" +
                "    (8, 'Adidas', 'Clothes'),\n" +
                "    (9, 'Clarks', 'Shoes'),\n" +
                "    (10, 'Ecco', 'Shoes'),\n" +
                "    (11, 'New Balance', 'Shoes'),\n" +
                "    (12, 'Timberland', 'Shoes'),\n" +
                "    (13, 'LOreal', 'Cosmetics'),\n" +
                "    (14, 'Maybelline', 'Cosmetics'),\n" +
                "    (15, 'Nivea', 'Cosmetics'),\n" +
                "    (16, 'Avon', 'Cosmetics'),\n" +
                "    (17, 'Gardena', 'Garden Tools'),\n" +
                "    (18, 'Bosch Garden', 'Garden Tools'),\n" +
                "    (19, 'Stihl', 'Garden Tools'),\n" +
                "    (20, 'Fiskars', 'Garden Tools');";

        String sql_insert_Boss = "INSERT INTO Boss ( name, income) VALUES\n" +
                "('Artur', 0.00);";
     try (Statement st1 = conection.createStatement()) {
         st1.executeUpdate(sql_create_Boss);
         System.out.println("Created Boss");
     }
     try (Statement st2 = conection.createStatement()) {
         st2.executeUpdate(sql_create_Departments);
         System.out.println("Created Departments");
     }
     try (Statement st3 = conection.createStatement()) {
         st3.executeUpdate(sql_create_Brands);
         System.out.println("Created Brands");
     }
     try (Statement st4 = conection.createStatement()) {
         st4.executeUpdate(sql_create_Managers);
         System.out.println("Created Managers");
     }
     try (Statement st5 = conection.createStatement()) {
         st5.executeUpdate(sql_create_Sellers);
         System.out.println("Created Sellers");
     }
     try (Statement st6 = conection.createStatement()) {
         st6.executeUpdate(sql_create_Products);
         System.out.println("Created Products");
     }
     try (Statement st7 = conection.createStatement()) {
         st7.executeUpdate(sql_create_Reviews);
         System.out.println("Created Reviews");
     }
     try (Statement st8 = conection.createStatement()) {
         st8.executeUpdate(sql_insert_Boss);
         System.out.println("Updated Boss");
     }
     try (Statement st9 = conection.createStatement()) {
         st9.executeUpdate(sql_insert_Departments);
         System.out.println("Updated Departments");
     }
     try (Statement st10 = conection.createStatement()) {
         st10.executeUpdate(sql_insert_Managers);
         System.out.println("Updated Managers");
     }
     try (Statement st11 = conection.createStatement()) {
         st11.executeUpdate(sql_insert_Brands);
         System.out.println("Updated Brands");
     }
     try (Statement st12 = conection.createStatement()) {
         st12.executeUpdate(sql_insert_Sellers);
         System.out.println("Updated Sellers");
     }
     try (Statement st13 = conection.createStatement()) {
         st13.executeUpdate(sql_insert_Products);
         System.out.println("Updated Products");
     }
    }




//    public void setDepartmentsFromDB() throws SQLException {
//        String sql = "SELECT * FROM Department";
//        PreparedStatement st = conection.prepareStatement(sql);
//        ResultSet rs = st.executeQuery();
//        while (rs.next()) {
//            department.add(new Department(rs.getString("name")));
//        }
//    }


//    public void setManagersFromDB() throws SQLException {
//        String sql = "SELECT * FROM Manager";
//        PreparedStatement st = conection.prepareStatement(sql);
//        ResultSet rs = st.executeQuery();
//        while (rs.next()) {
//            int id = rs.getInt("id");
//            String name = rs.getString("name");
//            String depname = rs.getString("department");
//            float income = rs.getFloat("income");
//            float commission = rs.getFloat("commission");
//            int experience = rs.getInt("experience");
//            managers.add(new Manager(id, name,getDep(depname), income, commission, experience));
//        }
//    }
//
//    public Department getDep(String depName) {
//        if (depName == null) return null;
//        for (Department dep : department) {
//            if (depName.trim().equalsIgnoreCase(dep.getName().trim())) {
//                return dep;
//            }
//        }
//        return null;
//    }

//    public void setSellersFromDB(int min,int max) throws SQLException {
//        Random rand = new Random();
//        String sql = "SELECT * FROM Seler";
//        PreparedStatement st = conection.prepareStatement(sql);
//        ResultSet rs = st.executeQuery();
//        while (rs.next()) {
//            int id = rs.getInt("id");
//            String name = rs.getString("name");
//            String depname = rs.getString("department");
//            float income = rs.getFloat("salary");
//            float commision = rs.getFloat("commission");
//            int salesCount = rs.getInt("salescount");
//            float rating = rs.getFloat("rating");
//            int experience = min + rand.nextInt(max - min);
////            int experience = rs.getInt("experience_years");
//            sellers.add(new Seller(id, name,getDep(depname),income, commision,experience, salesCount, rating));
////            for (Department dep : department) {
////                if (departmentname.equals(dep.getName())) {
////                    sellers.add(new Seller(id, name, dep, income, commision, salesCount, rating, experience));
////                }
////            }
//        }
//    }

//    public void setBrandFromDB() throws SQLException {
//        String sql = "SELECT * FROM Brand";
//        PreparedStatement st = conection.prepareStatement(sql);
//        ResultSet rs = st.executeQuery();
//        while (rs.next()) {
//            String name = rs.getString("name");
//            String depname = rs.getString("department");
//            brands.add(new Brand(name, getDep(depname)));
//        }
//    }
//
//    public void setProductsFromDB() throws SQLException {
//        String sql = "SELECT * FROM Product";
//        PreparedStatement st = conection.prepareStatement(sql);
//        ResultSet rs = st.executeQuery();
//        while (rs.next()) {
//            String name = rs.getString("name");
//            float price = rs.getFloat("price");
//            int quantity = rs.getInt("quantity");
//            String description = rs.getString("description");
//            String brand = rs.getString("brand");
//            Brand brand1 = getBrand(brand);
//            if (brand1 == null) {
//                System.out.println("❌ Brand nie znaleziono: " + brand);
//                continue; // albo throw new RuntimeException()
//            }
//
//            products.add(new Product(name, price, quantity, description, getBrand(brand)));
//        }
//    }
//
//    public Brand getBrand(String brandName) {
//        if (brandName == null) return null;
//        for (Brand brand : brands) {
//            if (brandName.trim().equalsIgnoreCase(brand.getName().trim())) {
//                return brand;
//            }
//        }
//        return null;
//    }

//    public void setBossFromDB() throws SQLException {
//        String sql = "SELECT * FROM Boss";
//        PreparedStatement st = conection.prepareStatement(sql);
//        ResultSet rs = st.executeQuery();
//        while (rs.next()) {
//            int id = rs.getInt("unik_id");
//            String name = rs.getString("name");
//            float income = rs.getFloat("income");
////            boss = new Boss();
//        }
//    }

//    public void updateQuantityInDB(Product product, int quantity) throws SQLException {
//        String sql = "UPDATE Product SET quantity = ? WHERE name = ?";
//        PreparedStatement st = conection.prepareStatement(sql);
//        st.setInt(1, quantity);
//        st.setString(2, product.getName());
//        st.executeUpdate();
//    }

    public void updateDataBase(Product product, Seller seller, Manager manager) throws SQLException {
        String sql = "UPDATE Product SET quantity = ? WHERE name = ?";
        PreparedStatement st = conection.prepareStatement(sql);
        st.setInt(1, product.getQuantity());
        st.setString(2, product.getName());
        st.executeUpdate();

        String sql1 = "UPDATE Seler SET salescount = ?, salary = ? WHERE name = ?";
        PreparedStatement st1 = conection.prepareStatement(sql1);
        st1.setInt(1,seller.getsalesCount());
        st1.setFloat(2,seller.getIncome());
        st1.setString(3, seller.getName());
        st1.executeUpdate();

        String sql2 = "UPDATE Manager SET income = ? WHERE name = ?";
        PreparedStatement st2 = conection.prepareStatement(sql2);
        st2.setFloat(1, manager.getIncome());
        st2.setString(2, manager.getName());
        st2.executeUpdate();

        String sql3 = "UPDATE Boss SET income = ?";
        PreparedStatement st3 = conection.prepareStatement(sql3);
        st3.setFloat(1,     product.getPrice() - (product.getPrice()/100*seller.getCommision() + product.getPrice()/100*manager.getCommision()));
        st3.executeUpdate();
    }

    public void cleanDB() throws SQLException {
        String sql1 = "DROP TABLE Boss";
        String sql2 = "DROP TABLE Departments";
        String sql3 = "DROP TABLE Brands";
        String sql4 = "DROP TABLE Managers";
        String sql5 = "DROP TABLE Products";
        String sql6 = "DROP TABLE Sellers";
        String sql7 = "DROP TABLE Reviews";
        PreparedStatement st1 = conection.prepareStatement(sql1);
        PreparedStatement st2 = conection.prepareStatement(sql2);
        PreparedStatement st3 = conection.prepareStatement(sql3);
        PreparedStatement st4 = conection.prepareStatement(sql4);
        PreparedStatement st5 = conection.prepareStatement(sql5);
        PreparedStatement st6 = conection.prepareStatement(sql6);
        PreparedStatement st7 = conection.prepareStatement(sql7);
        st1.executeUpdate();
        st2.executeUpdate();
        st3.executeUpdate();
        st4.executeUpdate();
        st5.executeUpdate();
        st6.executeUpdate();
        st7.executeUpdate();
    }

    public List<Department> getDepartments() {
        return department;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public  List<Brand> getBrands() {
        return brands;
    }

    public List<Product> getProducts() {
        return products;
    }

    public static Connection getConnection(){
        return conection;
    }

    public  Database getDatabase(){
        return this;
    }
}

