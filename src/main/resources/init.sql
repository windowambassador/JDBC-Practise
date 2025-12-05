-- Создаем таблицу users
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT,
    balance DECIMAL(19,2) DEFAULT 0.00
);

-- Вставляем тестовые данные
INSERT INTO users (name, age, balance) VALUES
('Alice', 30, 1000.00),
('Bob', 25, 500.00),
('Charlie', 35, 2000.00);