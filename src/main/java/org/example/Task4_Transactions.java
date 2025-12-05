package org.example;

import org.example.dao.UserDaoImpl;
import org.example.model.User;
import org.example.util.DatabaseConnection;
import java.sql.PreparedStatement;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class Task4_Transactions {

    public static void transfer(int fromId, int toId, BigDecimal amount) {
        String sqlWithdraw = "UPDATE users SET balance = balance - ? WHERE id = ?";
        String sqlDeposit = "UPDATE users SET balance = balance + ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Начинаем транзакцию

            try (PreparedStatement withdrawStmt = conn.prepareStatement(sqlWithdraw);
                 PreparedStatement depositStmt = conn.prepareStatement(sqlDeposit)) {

                // Снимаем деньги
                withdrawStmt.setBigDecimal(1, amount);
                withdrawStmt.setInt(2, fromId);
                int rows1 = withdrawStmt.executeUpdate();

                // Добавляем деньги
                depositStmt.setBigDecimal(1, amount);
                depositStmt.setInt(2, toId);
                int rows2 = depositStmt.executeUpdate();

                if (rows1 > 0 && rows2 > 0) {
                    conn.commit(); // Фиксируем транзакцию
                    System.out.println("✅ Перевод выполнен успешно");
                } else {
                    conn.rollback(); // Откатываем при ошибке
                    System.out.println("❌ Ошибка: недостаточно средств или неверный ID");
                }
            } catch (SQLException e) {
                conn.rollback(); // Откат при исключении
                throw new RuntimeException("Ошибка транзакции", e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        UserDaoImpl dao = new UserDaoImpl();

        // Показать балансы до перевода
        System.out.println("До перевода:");
        dao.findAll().forEach(System.out::println);

        // Выполнить перевод
        transfer(1, 2, BigDecimal.valueOf(200.0));

        // Показать балансы после
        System.out.println("\nПосле перевода:");
        dao.findAll().forEach(System.out::println);
    }
}