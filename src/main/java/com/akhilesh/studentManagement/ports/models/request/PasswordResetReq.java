package com.akhilesh.studentManagement.ports.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PasswordResetReq {
    @NotNull
    @NotBlank
    private final String userId;
    @NotNull
    @NotBlank
    private final String secretToken;
    @NotNull
    @NotBlank
    private final String newPassword;

    public PasswordResetReq(String userId, String secretToken, String newPassword) {
        this.userId = userId;
        this.secretToken = secretToken;
        this.newPassword = newPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
