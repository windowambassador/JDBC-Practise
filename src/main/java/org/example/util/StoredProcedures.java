package org.example.util;

import java.sql.*;

public class StoredProcedures {

    public static void createIncreaseBalanceProcedure() {
        // H2 не поддерживает DROP PROCEDURE, но поддерживает DROP ALIAS
        String dropAlias = "DROP ALIAS IF EXISTS increase_balance";
        String createAlias = """
            CREATE ALIAS increase_balance AS $$
            void increaseBalance(Connection conn, long userId, double amount) throws SQLException {
                try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE users SET balance = balance + ? WHERE id = ?")) {
                    stmt.setBigDecimal(1, java.math.BigDecimal.valueOf(amount));
                    stmt.setLong(2, userId);
                    stmt.executeUpdate();
                }
            }
            $$;
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(dropAlias);      // Удаляем старый алиас, если есть
            stmt.execute(createAlias);    // Создаём новый
            System.out.println("✅ Алиас 'increase_balance' создан (эмуляция хранимой процедуры)");
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при создании алиаса: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void callIncreaseBalance(long userId, double amount) {
        String sql = "{CALL increase_balance(?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setLong(1, userId);
            cstmt.setDouble(2, amount); // H2 в алиасе принимает double

            cstmt.execute();
            System.out.printf("✅ Баланс пользователя %d увеличен на %.2f%n", userId, amount);
        } catch (SQLException e) {
            System.err.println("❌ Ошибка вызова алиаса: " + e.getMessage());
            e.printStackTrace();
        }
    }
}