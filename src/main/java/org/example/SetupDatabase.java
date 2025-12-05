package org.example;

import org.example.util.DatabaseConnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

public class SetupDatabase {
    public static void initDatabase() {
        try (InputStream is = SetupDatabase.class.getClassLoader().getResourceAsStream("init.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is));
             Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }

            stmt.execute(sql.toString());
            System.out.println("✅ База данных инициализирована");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initDatabase();
    }
}