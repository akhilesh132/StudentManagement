package com.akhilesh.studentManagement.ports.models.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AuthenticationReq {

    @NotNull
    @NotEmpty
    private final String userId;
    @NotNull
    @NotEmpty
    private final String password;

    public AuthenticationReq(String userId, String password) {
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