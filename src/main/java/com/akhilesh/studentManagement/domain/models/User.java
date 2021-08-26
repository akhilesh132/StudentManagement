package com.akhilesh.studentManagement.domain.models;

public final class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username can't be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password can't be null or empty");
        }
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getUserId() {
        return this.username;
    }

    public String getUserEmail() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                "userId='" + username + '\'' +
                ", password='" + "xxx" + '\'' +
                '}';
    }
}
