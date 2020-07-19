package com.example.userapp;

public class User {
    private String firstName;
    private String lastName;

    private boolean gender;

    private String email;
    private String password;

    public User(String firstName, String lastName, boolean gender, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
