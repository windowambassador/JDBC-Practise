package org.example;

import org.example.dao.UserDaoHikariImpl;
import org.example.model.User;
import org.example.util.HikariCPDataSource;

import java.math.BigDecimal;
import java.util.Scanner;

public class Task10_FinalProject {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDaoHikariImpl userDao = new UserDaoHikariImpl();

    public static void main(String[] args) {

        System.out.println("🎉 Добро пожаловать в Менеджер Пользователей!");

        while (true) {
            showMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1 -> listAllUsers();
                case 2 -> createUser();
                case 3 -> findUserById();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 6 -> transferMoney();
                case 7 -> {
                    System.out.println("👋 До свидания!");
                    HikariCPDataSource.close();
                    return;
                }
                default -> System.out.println("❌ Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Меню ===");
        System.out.println("1. Показать всех пользователей");
        System.out.println("2. Добавить пользователя");
        System.out.println("3. Найти пользователя по ID");
        System.out.println("4. Обновить пользователя");
        System.out.println("5. Удалить пользователя");
        System.out.println("6. Перевести деньги между пользователями");
        System.out.println("7. Выход");
    }

    private static void listAllUsers() {
        System.out.println("\n=== Все пользователи ===");
        userDao.findAll().forEach(System.out::println);
    }

    private static void createUser() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        int age = getIntInput("Введите возраст: ");
        double balance = getDoubleInput("Введите баланс: ");

        User user = new User(name, age);
        user.setBalance(BigDecimal.valueOf(balance));
        userDao.create(user);
        System.out.println("✅ Пользователь создан: " + user);
    }

    private static void findUserById() {
        long id = getLongInput("Введите ID пользователя: ");
        User user = userDao.findById(id);
        if (user != null) {
            System.out.println("✅ Найден: " + user);
        } else {
            System.out.println("❌ Пользователь не найден");
        }
    }

    private static void updateUser() {
        long id = getLongInput("Введите ID пользователя для обновления: ");
        User user = userDao.findById(id);
        if (user == null) {
            System.out.println("❌ Пользователь не найден");
            return;
        }

        System.out.print("Новое имя (оставьте пустым для сохранения текущего): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            user.setName(name);
        }

        int age = getIntInput("Новый возраст (0 для пропуска): ");
        if (age > 0) {
            user.setAge(age);
        }

        double balance = getDoubleInput("Новый баланс (0 для пропуска): ");
        if (balance > 0) {
            user.setBalance(BigDecimal.valueOf(balance));
        }

        userDao.update(user);
        System.out.println("✅ Пользователь обновлён: " + user);
    }

    private static void deleteUser() {
        long id = getLongInput("Введите ID пользователя для удаления: ");
        userDao.delete(id);
        System.out.println("✅ Пользователь удалён");
    }

    private static void transferMoney() {
        int fromId = getIntInput("ID отправителя: ");
        int toId = getIntInput("ID получателя: ");
        double amount = getDoubleInput("Сумма перевода: ");

        Task4_Transactions.transfer(fromId, toId, BigDecimal.valueOf(amount));
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Введите целое число.");
            }
        }
    }

    private static long getLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Введите целое число.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Введите число с точкой (например, 100.50).");
            }
        }
    }
}