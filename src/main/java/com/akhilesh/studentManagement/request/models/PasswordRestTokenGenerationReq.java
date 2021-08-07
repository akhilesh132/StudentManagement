package com.akhilesh.studentManagement.request.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PasswordRestTokenGenerationReq {

    private final String email;

    @JsonCreator
    public PasswordRestTokenGenerationReq(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
