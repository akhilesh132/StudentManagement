package com.akhilesh.hrms.security.domain.models;

public final class User {
    private final Username username;
    private final Password password;

    public User(Username username, Password password) {
        if (username == null) {
            throw new IllegalArgumentException("username can't be null");
        }
        if (password == null) {
            throw new IllegalArgumentException("password can't be null");
        }
        this.username = username;
        this.password = password;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public User withPassword(Password password) {
        return new User(this.username, password);
    }
}
