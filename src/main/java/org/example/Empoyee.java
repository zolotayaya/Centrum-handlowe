package org.example;

import java.sql.Connection;

public abstract class Empoyee {
    private int id;
    private String name;
    private Department department;
    private float income;
    private float commision;
    public float calculate(){
        return 0;
    }
    public String showInformation(){
        Database db = new Database();
        Connection conn = db.getConnection();

        return "o";
    }
}
