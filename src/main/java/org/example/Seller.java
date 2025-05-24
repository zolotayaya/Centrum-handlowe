package org.example;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.*;

public class Seller extends Empoyee {
    private int id;
    private String name;
    private String department;
    private float income;
    private float experience;
    private int salescount = 0;
    private float percent_income;
    private Brand brand;
    private float rating;
    public void setExperience(int min, int max) throws SQLException {
        Random rand = new Random();
        List<String> names = new ArrayList<String>();
        Database db = new Database();
        Connection conn = db.getConnection();
        String sql1 = "SELECT name FROM sellers";
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        ResultSet rs = ps1.executeQuery();
        while (rs.next()) {
            names.add(rs.getString("name"));
        }
        String sql2 = "UPDATE sellers SET experience = ? WHERE name = ?";
        PreparedStatement ps2 = conn.prepareStatement(sql2);
        for (String name : names) {
            int experience = min + rand.nextInt(max - min + 1);
            ps2.setInt(1, experience);
            ps2.setString(2, name);
            ps2.executeUpdate();
        }
    }
    public void saleProduct(Product product, int id) throws SQLException {
        salescount++;
        income += product.getPrice()/100*(int) percent_income;
        Database db = new Database();
        Connection conn = db.getConnection();
        String sql1 = "UPDATE sellers SET count_of_sales=? WHERE unik_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql1);
        ps.setInt(1, salescount);
        ps.setInt(2, id);
        ps.executeUpdate();
    }
    public void addToPromotionQueue(){

    }
    public float getAvarageRating(){
        return 0;
    }

    public void createSellers(int seler) throws SQLException {
        Database db = new Database();
        Connection conn = db.getConnection();
            String sql = "SELECT * FROM sellers WHERE unik_id= ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, seler);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.id = rs.getInt("unik_id");
                this.name = rs.getString("name");
                this.salescount = rs.getInt("count_of_sales");
                this.department = rs.getString("department_id");
                this.percent_income = rs.getFloat("percent_income");
                this.experience = rs.getFloat("experience");
            }
    }
    @Override
    public String showInformation(){
        return this.name;
    }
        public void showinformation(){
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Department: " + department);
            System.out.println("Income: " + income);
            System.out.println("Experience: " + experience);
            System.out.println("Sales count: " + salescount);
        }
        }

