package org.example;

import org.example.dao.UserDaoImpl;
import org.example.model.User;

import java.math.BigDecimal;

public class Task2_PreparedStatementAdd {
    public static void main(String[] args) {


        UserDaoImpl dao = new UserDaoImpl();
        User newUser = new User("Diana", 28);
        newUser.setBalance(BigDecimal.valueOf(750.0));

        dao.create(newUser);
        System.out.println("✅ Пользователь добавлен: " + newUser);
    }
}