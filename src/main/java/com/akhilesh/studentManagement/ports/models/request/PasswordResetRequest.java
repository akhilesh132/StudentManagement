package com.akhilesh.studentManagement.ports.models.request;

public class PasswordResetRequest {

    private final String userId;
    private final String secretToken;
    private final String newPassword;

    public PasswordResetRequest(String userId, String secretToken, String newPassword) {
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
