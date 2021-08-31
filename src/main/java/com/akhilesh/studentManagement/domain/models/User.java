package com.akhilesh.studentManagement.domain.models;

import com.akhilesh.studentManagement.security.domain.models.Password;

public final class User {
    private final String username;
    private final Password password;

    public User(String username, Password password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username can't be null or empty");
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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                "userId='" + username + '\'' +
                ", password='" + "xxx" + '\'' +
                '}';
    }
}
