package com.akhilesh.studentManagement.security.domain.models;

public class User {
    private final Username username;
    private final Password password;

    public User(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    public Username getUsername() {
        return username;
    }

}
