package edu.Task2.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;

    public User(String firstName, String lastName, int age) {
        this.id = generateRandomId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    private Long generateRandomId() {
        // Generates a random ID between 0 and 2000
        return ThreadLocalRandom.current().nextLong(2000);
    }

    @Override
    public String toString() {
        return String.format("User{uid=%d, firstName='%s', lastName='%s', age=%d}", id, firstName, lastName, age);
    }
}
