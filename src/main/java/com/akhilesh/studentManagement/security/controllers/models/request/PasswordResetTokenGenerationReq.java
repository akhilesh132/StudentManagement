package com.akhilesh.studentManagement.security.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PasswordResetTokenGenerationReq {

    private final String email;

    @JsonCreator
    public PasswordResetTokenGenerationReq(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
