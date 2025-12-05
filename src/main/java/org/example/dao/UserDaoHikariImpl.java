package org.example.dao;

import org.example.model.User;
import org.example.util.HikariCPDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHikariImpl implements UserDao {
    @Override
    public void create(User user) {
        String sql = "INSERT INTO users (name, age, balance) VALUES (?, ?, ?)";
        try (Connection conn = HikariCPDataSource.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.setBigDecimal(3, user.getBalance());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                }
            }
            System.out.println("User created via Hikari: " + user);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = HikariCPDataSource.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by ID", e);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection conn = HikariCPDataSource.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all users", e);
        }
        return users;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name = ?, age = ?, balance = ? WHERE id = ?";
        try (Connection conn = HikariCPDataSource.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.setBigDecimal(3, user.getBalance());
            pstmt.setLong(4, user.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated via Hikari: " + user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = HikariCPDataSource.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted via Hikari with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getBigDecimal("balance")
        );
    }
}