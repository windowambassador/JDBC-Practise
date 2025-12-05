package org.example;

import org.example.dao.UserDaoHikariImpl;
import org.example.model.User;

import java.math.BigDecimal;

public class Task6_HikariCP {
    public static void main(String[] args) {

        UserDaoHikariImpl dao = new UserDaoHikariImpl();

        User user = new User("HikariUser", 33);
        user.setBalance(BigDecimal.valueOf(1000.0));
        dao.create(user);

        System.out.println("✅ Пользователь через Hikari: " + dao.findById(user.getId()));
    }
}