package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        ArrayList<School> schools = Parser.parseCsv("src/main/resources/schools.csv");
        try {
            Database.connectDB();
            Database.createTable();
            Database.addDataToTable(schools);
            Database.firstTask();
            Database.secondTask();
            Database.thirdTask();
            Database.closeDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}