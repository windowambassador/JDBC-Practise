package org.example;

import org.example.model.User;
import org.example.util.DatabaseConnection;
import org.example.util.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Task7_RowMapper {
    public static <T> List<T> query(String sql, RowMapper<T> rowMapper) {
        List<T> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                T obj = rowMapper.mapRow(rs);
                results.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения запроса", e);
        }
        return results;
    }

    public static void main(String[] args) {

        String sql = "SELECT * FROM users";

        RowMapper<User> userMapper = rs -> new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getBigDecimal("balance")
        );

        List<User> users = query(sql, userMapper);
        users.forEach(System.out::println);
    }
}