package com.akhilesh.studentManagement.security.domain.models;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public final class PasswordResetCode {

    private final Username username;
    private final Secret secret;
    private final LocalDateTime creationTime = LocalDateTime.now();

    private PasswordResetCode(Username username) {
        if (username == null) {
            throw new IllegalArgumentException("username can't be null");
        }
        this.username = username;
        this.secret = new Secret();
    }

    public PasswordResetCode(Username username, Secret secret) {
        this.username = username;
        this.secret = secret;
    }

    public static PasswordResetCode forUser(User user) {
        return new PasswordResetCode(user.getUsername());
    }

    public boolean matches(Secret secret){
        return this.secret.matches(secret);
    }
    public String value() {
        return secret.value();
    }

    public boolean isExpired() {
        return creationTime.plusMinutes(5).isBefore(now());
    }

    public Username getUsername() {
        return username;
    }
}
