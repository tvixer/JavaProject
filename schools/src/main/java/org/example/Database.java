package org.example;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Connection connection;
    private static Statement statement;

    //подключение к Базе данных
    public static void connectDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
        statement = connection.createStatement();
    }

    //закрытия соединения с Базой данных
    public static void closeDB() throws SQLException {
        statement.close();
        connection.close();
    }

    //создание таблицы
    public static void createTable() throws SQLException {
        statement.execute(
                "CREATE TABLE IF NOT EXISTS School (" +
                        "school TEXT, " +
                        "students INTEGER, " +
                        "country TEXT, " +
                        "expenditure FLOAT, " +
                        "math FLOAT);"
        );
    }

    //добавление данных в таблицу
    public static void addDataToTable(ArrayList<School> schools){
        schools.forEach(school -> {
            try {
                statement.execute(String.format(
                        "INSERT INTO School " +
                                "VALUES ('%s', '%s', '%s', '%s', '%s')",
                        school.getName(), school.getStudents(), school.getCountry(), school.getExpenditure(), school.getMath())
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    //первое задание
    public static void firstTask() throws SQLException {
        String firstSql = "" +
                "SELECT country, AVG(students) " +
                "FROM School " +
                "WHERE country IN " +
                "      ('Tulare', 'Fresno', 'Merced', 'Sacramento', " +
                "       'Kern', 'San Joaquin', 'Monterey', 'San Diego', " +
                "       'Los Angeles', 'Ventura') " +
                "GROUP BY country;";

        Map<String, Double> result = new HashMap<>();
        ResultSet resSql = statement.executeQuery(firstSql);
        while (resSql.next()) {
            result.put(
                    resSql.getString("country"),
                    Double.parseDouble(resSql.getString("avg(students)")));
        }

        EventQueue.invokeLater(() -> {
            Graph graph = new Graph(result);
            graph.setVisible(true);
        });
    }

    //второе задание
    public static void secondTask() throws SQLException {
        String secondSql = "SELECT AVG(expenditure) " +
                "FROM School " +
                "WHERE country IN ('Fresno', 'Contra Costa', 'El Dorado', 'Glenn') " +
                "  AND expenditure > 10;";

        String res = statement.executeQuery(secondSql).getString("AVG(expenditure)");
        System.out.printf("Среднее количество расходов: %.2f", Double.parseDouble(res));
    }

    //третье задание
    public static void thirdTask() throws SQLException {
        String thirdSql = "SELECT school, MAX(math) " +
                "FROM School " +
                "WHERE (students >= 5000 AND students <= 7500) OR (students >= 10000 AND students <= 11000);";
        String res = statement.executeQuery(thirdSql).getString("school");
        System.out.printf("\nУчебное заведение, с количеством студентов равному от 5000 до 7500 и с 10000 до 11000," +
                " с самым высоким показателем по математике: %s", res);
    }
}
