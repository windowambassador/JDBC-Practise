package org.example.model;

import java.math.BigDecimal;
import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private int age;
    private BigDecimal balance;

    public User() {}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(Long id, String name, int age, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.balance = balance;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(balance, user.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, balance);
    }
}