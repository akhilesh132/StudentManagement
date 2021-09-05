package com.akhilesh.studentManagement.security.controllers.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class PasswordResetRequest {
    @NotNull
    @NotBlank
    private final String username;
    @NotNull
    @NotBlank
    private final String secretToken;
    @NotNull
    @NotBlank
    private final String updatedPassword;

    public PasswordResetRequest(String username, String secretToken, String updatedPassword) {
        if (isEmpty(username)) {
            throw new IllegalArgumentException("username can't be null or empty");
        }
        if (isEmpty(secretToken)) {
            throw new IllegalArgumentException("secret token must be provided");
        }
        if (isEmpty(updatedPassword)) {
            throw new IllegalArgumentException("new password can't be null or empty");
        }
        this.username = username;
        this.secretToken = secretToken;
        this.updatedPassword = updatedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public String getUpdatedPassword() {
        return updatedPassword;
    }
}
