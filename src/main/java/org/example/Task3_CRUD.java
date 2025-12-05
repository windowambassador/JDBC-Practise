package org.example;

import org.example.dao.UserDaoImpl;
import org.example.model.User;

import java.math.BigDecimal;
import java.util.List;

public class Task3_CRUD {
    public static void main(String[] args) {


        UserDaoImpl dao = new UserDaoImpl();

        // CREATE
        User user = new User("Eve", 22);
        user.setBalance(BigDecimal.valueOf(300.0));
        dao.create(user);

        // READ (find all)
        List<User> allUsers = dao.findAll();
        System.out.println("\n=== Все пользователи ===");
        allUsers.forEach(System.out::println);

        // UPDATE
        User toUpdate = dao.findById(user.getId());
        if (toUpdate != null) {
            toUpdate.setName("Eve Updated");
            toUpdate.setBalance(BigDecimal.valueOf(350.0));
            dao.update(toUpdate);
        }

        // DELETE
        dao.delete(user.getId());
        System.out.println("\n✅ Пользователь удалён");

        // Проверка после удаления
        System.out.println("После удаления:");
        dao.findAll().forEach(System.out::println);
    }
}