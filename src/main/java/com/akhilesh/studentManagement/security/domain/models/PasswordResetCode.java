package com.akhilesh.studentManagement.security.domain.models;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public final class PasswordResetCode {
    private final Username username;
    private final SecretCode secretCode;
    private final LocalDateTime creationTime = LocalDateTime.now();

    private PasswordResetCode(Username username) {
        if (username == null) {
            throw new IllegalArgumentException("username cann't be null");
        }
        this.username = username;
        secretCode = new SecretCode();
    }

    public PasswordResetCode(Username username, SecretCode secretCode) {
        this.username = username;
        this.secretCode = secretCode;
    }

    public static PasswordResetCode forUser(User user) {
        return new PasswordResetCode(user.getUsername());
    }

    public boolean isExpired() {
        return creationTime.plusMinutes(5).isBefore(now());
    }

    public String value() {
        return secretCode.value();
    }

    public Username getUsername() {
        return username;
    }
}
