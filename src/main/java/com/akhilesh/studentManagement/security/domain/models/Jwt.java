package com.akhilesh.studentManagement.security.domain.models;

public class Jwt{

    private final String value;


    public Jwt(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
