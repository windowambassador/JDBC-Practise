package org.example;

public class InitDB {
    public static void main(String[] args) {
        System.out.println("🔄 Инициализация базы данных...");
        SetupDatabase.initDatabase();
        System.out.println("✅ Готово! Теперь можно запускать задания.");
    }
}