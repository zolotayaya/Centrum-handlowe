package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
public class Database {
    private List<Seller> sellers;
    private List<Manager> managers;
    private Boss boss;
    private List<Department> department;
    private List<Product> products;
    private List<PurchaseRecord> sales;
    private Connection con;

    public Database() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "datax";

        try {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Connection getConnection() {
        return con;
    }
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}