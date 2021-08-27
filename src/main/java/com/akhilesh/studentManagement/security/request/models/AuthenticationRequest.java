package com.akhilesh.studentManagement.security.request.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AuthenticationRequest {

    @NotNull
    @NotEmpty
    private final String userId;
    @NotNull
    @NotEmpty
    private final String password;

    public AuthenticationRequest(String userId, String password) {
        if (isEmpty(userId)) {
            throw new IllegalArgumentException("userId can't be null or empty");
        }
        if (isEmpty(password)) {
            throw new IllegalArgumentException("password can't be null or empty");
        }
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
