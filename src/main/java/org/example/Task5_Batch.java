package org.example;

import org.example.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class Task5_Batch {
    public static void insertBatchUsers(int count) {
        String sql = "INSERT INTO users (name, age, balance) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                Random random = new Random();
                for (int i = 0; i < count; i++) {
                    String name = "User_" + (i + 1);
                    int age = 18 + random.nextInt(50);
                    double balance = 100.0 + random.nextDouble() * 900.0;

                    pstmt.setString(1, name);
                    pstmt.setInt(2, age);
                    pstmt.setBigDecimal(3, java.math.BigDecimal.valueOf(balance).setScale(2, java.math.RoundingMode.HALF_UP));

                    pstmt.addBatch();

                    if (i % 1000 == 0 && i > 0) {
                        pstmt.executeBatch();
                        System.out.println("Вставлено: " + i + " записей");
                    }
                }

                pstmt.executeBatch(); // Последняя партия
                conn.commit();
                System.out.println("✅ Всего вставлено: " + count + " пользователей");

            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Ошибка при выполнении batch", e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        insertBatchUsers(10_000);
    }
}