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
    private final String password;

    public PasswordResetRequest(String username, String secretToken, String password) {
        if (isEmpty(username)) {
            throw new IllegalArgumentException("username can't be null or empty");
        }
        if (isEmpty(secretToken)) {
            throw new IllegalArgumentException("secret token must be provided");
        }
        if (isEmpty(password)) {
            throw new IllegalArgumentException("new password can't be null or empty");
        }
        this.username = username;
        this.secretToken = secretToken;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public String getPassword() {
        return password;
    }
}
