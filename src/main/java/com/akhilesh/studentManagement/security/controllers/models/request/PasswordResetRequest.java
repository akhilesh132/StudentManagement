package com.akhilesh.studentManagement.security.controllers.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PasswordResetRequest {
    @NotNull
    @NotBlank
    private final String username;
    @NotNull
    @NotBlank
    private final String secretToken;
    @NotNull
    @NotBlank
    private final String newPassword;

    public PasswordResetRequest(String username, String secretToken, String newPassword) {
        this.username = username;
        this.secretToken = secretToken;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
