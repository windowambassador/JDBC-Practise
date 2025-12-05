package org.example;

import org.example.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn.isValid(5)) {
                System.out.println("✅ Подключение к базе данных успешно!");
            } else {
                System.out.println("❌ Подключение не удалось");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка подключения: " + e.getMessage());
        }
    }
}