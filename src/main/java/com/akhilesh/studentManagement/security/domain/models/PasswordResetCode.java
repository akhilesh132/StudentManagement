package com.akhilesh.studentManagement.security.domain.models;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class PasswordResetCode {
    private final Username username;
    private final RandomSecret secretCode;
    private final LocalDateTime creationTime;

    private PasswordResetCode(Username username) {
        this.username = username;
        secretCode = new RandomSecret();
        this.creationTime = now();
    }

    public static PasswordResetCode forUser(User user) {
        return new PasswordResetCode(user.getUsername());
    }

    public boolean isExpired() {
        return creationTime.plusMinutes(5).isBefore(now());
    }

    public String value(){
        return secretCode.value();
    }
}
