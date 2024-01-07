package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Parser {
    public static ArrayList<School> parseCsv(String path){
        ArrayList<School> schools = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))){
            reader.readLine();
            while (reader.ready()) {
                String[] info = reader.readLine().split(",");
                String school = info[2].substring(1, info[2].length() - 1);
                int students = Integer.parseInt(info[5]);
                String country = info[3].substring(1, info[3].length() - 1);
                double expenditure = Double.parseDouble(info[10]);
                double math = Double.parseDouble(info[14]);
                schools.add(new School(school, students, country, expenditure, math));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return schools;
    }
}
