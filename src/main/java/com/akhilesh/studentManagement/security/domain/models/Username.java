package com.akhilesh.studentManagement.security.domain.models;

public class Username {
    private final String username;

    public Username(String username) {
        this.username = username;
    }

    public String value() {
        return this.username;
    }
}
