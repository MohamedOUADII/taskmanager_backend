package com.example.taskmanager.dto;

import java.util.Optional;

public class UserUpdateDTO {
    private Optional<String> username = Optional.empty();
    private Optional<String> email = Optional.empty();

    public UserUpdateDTO() {}

    // Getters and setters

    public Optional<String> getUsername() {
        return username;
    }
    public void setUsername(Optional<String> username) {
        this.username = username;
    }
    public Optional<String> getEmail() {
        return email;
    }
    public void setEmail(Optional<String> email) {
        this.email = email;
    }
}
