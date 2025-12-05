package org.example;

import org.example.util.DatabaseConnection;

import java.sql.*;

public class Task8_SQLInjection {
    public static void dangerousQuery(String userInput) {
        String sql = "SELECT * FROM users WHERE name = '" + userInput + "'";
        System.out.println("⚠️ SQL-инъекция: " + sql);

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("Найден: %s (ID: %d)%n", rs.getString("name"), rs.getLong("id"));
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка инъекции: " + e.getMessage());
        }
    }

    public static void safeQuery(String userInput) {
        String sql = "SELECT * FROM users WHERE name = ?";
        System.out.println("✅ Безопасный запрос с PreparedStatement");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userInput);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("Найден: %s (ID: %d)%n", rs.getString("name"), rs.getLong("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {



        // Пример инъекции
        String maliciousInput = "' OR '1'='1";
        dangerousQuery(maliciousInput);

        System.out.println("\n---\n");

        // Безопасный способ
        safeQuery("Alice");
    }
}