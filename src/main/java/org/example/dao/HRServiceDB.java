package org.example.dao;
import jdk.jshell.spi.SPIResolutionException;
import org.example.database.Database;
import org.example.model.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Klasa {@code HRServiceDB} odpowiada za operacje rekrutacyjne związane z bazą danych.
 * Umożliwia inicjalizację quizu oraz tworzenie nowych pracowników w tabeli {@code Sellers}.
 */
public class HRServiceDB {
    /** Połączenie z bazą danych. */
    private Connection connection;
    /** Lista przechowująca pytania rekrutacyjne. */
    ArrayList<String> questions;
    /** Lista przechowująca odpowiedzi rekrutacyjne. */
    ArrayList<String> answers;

    /**
     * Konstruktor inicjalizuje połączenie z bazą danych oraz puste listy pytań i odpowiedzi.
     */
    public HRServiceDB() {
        this.connection = Database.getConnection();
        questions = new ArrayList<>();
        answers = new ArrayList<>();
    }
    /**
     * Pobiera pytania i odpowiedzi rekrutacyjne z bazy danych i zapisuje je do lokalnych list.
     *
     * @throws SQLException jeśli wystąpi błąd podczas zapytania do bazy danych
     */
    public void initialiseQuize() throws SQLException {
        String sql1 = "SELECT question_text FROM Questions";// Pobieranie pytań z tabeli Questions
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        ResultSet resultSet = preparedStatement1.executeQuery();
        while (resultSet.next()) {
            String questionText = resultSet.getString("question_text");
            questions.add(questionText);// dodajemy pytania do listy
        }
        String sql2 = "SELECT answer_text FROM Answers";// Pobieranie odpowiedzi z tabeli Answers
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        ResultSet resultSet1 = preparedStatement2.executeQuery();
        while (resultSet1.next()) {
            String answerText = resultSet1.getString("answer_text");
            answers.add(answerText);// dodajemy odpowiedzi do listy
        }
    }
    /**
     * Tworzy nowego pracownika (sprzedawcę) w tabeli {@code Sellers}.
     *
     * @param name       imię nowego pracownika
     * @param years      liczba lat doświadczenia
     * @param department nazwa działu, do którego przypisany jest pracownik
     * @return identyfikator (ID) nowo dodanego pracownika
     * @throws SQLException jeśli wystąpi błąd przy wstawianiu lub odczycie danych
     */
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
        String sql1 = "SELECT id FROM Sellers WHERE name = ?";// Zapytanie do pobrania ID nowo dodanego sprzedawcy
        try (PreparedStatement ps1 = connection.prepareStatement(sql1)) {
            ps1.setString(1, name);

            try (ResultSet rs = ps1.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");// zwracamy ID nowego pracownika
                } else {
                    throw new SQLException("The seller has no knowledge");
                }
            }
        }
    }
    /**
     * Zwraca listę pytań rekrutacyjnych.
     *
     * @return lista pytań
     */
    public ArrayList<String> getQuestions(){
        return questions;
    }// Metoda zwracająca listę wszystkich pytań
    /**
     * Zwraca listę odpowiedzi rekrutacyjnych.
     *
     * @return lista odpowiedzi
     */
    public ArrayList<String> getAnswers(){
        return answers;
    } // Metoda zwracająca listę wszystkich odpowiedzi
}
