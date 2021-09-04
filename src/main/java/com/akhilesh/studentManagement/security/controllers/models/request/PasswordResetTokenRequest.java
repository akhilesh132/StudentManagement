package com.akhilesh.studentManagement.security.controllers.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class PasswordResetTokenRequest {

    @NotNull
    @NotEmpty
    private final String username;

    @JsonCreator
    public PasswordResetTokenRequest(String username) {
        if (isEmpty(username)) {
            throw new IllegalArgumentException("username can't be null");
        }
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordResetTokenRequest that = (PasswordResetTokenRequest) o;

        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "PasswordResetTokenRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}
