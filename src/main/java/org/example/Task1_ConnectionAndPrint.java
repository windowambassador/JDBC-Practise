package org.example;

import org.example.util.DatabaseConnection;

import java.sql.*;

public class Task1_ConnectionAndPrint {
    public static void main(String[] args) {




        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("=== Список всех пользователей ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Имя: %s | Возраст: %d%n",
                        rs.getLong("id"), rs.getString("name"), rs.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}