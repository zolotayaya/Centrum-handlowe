package org.example.dao;
import jdk.jshell.spi.SPIResolutionException;
import org.example.database.Database;
import org.example.model.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HRServiceDB {
    private Connection connection;
    ArrayList<String> questions;
    ArrayList<String> answers;
    public HRServiceDB() {
        this.connection = Database.getConnection();
        questions = new ArrayList<>();
        answers = new ArrayList<>();
    }
    public void initialiseQuize() throws SQLException {
        String sql1 = "SELECT question_text FROM Questions";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        ResultSet resultSet = preparedStatement1.executeQuery();
        while (resultSet.next()) {
            String questionText = resultSet.getString("question_text");
            questions.add(questionText);
        }
        String sql2 = "SELECT answer_text FROM Answers";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        ResultSet resultSet1 = preparedStatement2.executeQuery();
        while (resultSet1.next()) {
            String answerText = resultSet1.getString("answer_text");
            answers.add(answerText);
        }
    }
    public int create_Employee_In_DB(String name, int years,String department) throws SQLException {
        String sql = "INSERT INTO Sellers (name, department,salary, experience_years, rating, commission, salescount)" +
                "VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, department);
        ps.setInt(3, 0);        // salary
        ps.setInt(4, years);    // experience_years
        ps.setInt(5, 0);        // rating
        ps.setInt(6, 10);       // commission
        ps.setInt(7, 0);
        ps.executeUpdate();
        System.out.println("Employee added");
        String sql1 = "SELECT id FROM Sellers WHERE name = ?";
        try (PreparedStatement ps1 = connection.prepareStatement(sql1)) {
            ps1.setString(1, name);

            try (ResultSet rs = ps1.executeQuery()) {
                if (rs.next()) {  // Критично важливо!
                    return rs.getInt("id");
                } else {
                    throw new SQLException("Продавець не знайдений");
                }
            }
        }
    }
    public ArrayList<String> getQuestions(){
        return questions;
    }
    public ArrayList<String> getAnswers(){
        return answers;
    }
}
