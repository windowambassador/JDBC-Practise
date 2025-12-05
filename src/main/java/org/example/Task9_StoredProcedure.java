package org.example;

import org.example.util.StoredProcedures;

public class Task9_StoredProcedure {
    public static void main(String[] args) {



        // Создаем процедуру
        StoredProcedures.createIncreaseBalanceProcedure();

        // Вызываем процедуру
        StoredProcedures.callIncreaseBalance(1, 500.0);

        // Проверяем результат
        System.out.println("После вызова процедуры:");
        new Task1_ConnectionAndPrint().main(new String[]{});
    }
}