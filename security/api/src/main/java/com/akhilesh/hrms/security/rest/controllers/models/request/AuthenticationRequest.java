package com.akhilesh.hrms.security.rest.controllers.models.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AuthenticationRequest {

    @NotNull
    @NotEmpty
    private final String username;
    @NotNull
    @NotEmpty
    private final String password;

    public AuthenticationRequest(String username, String password) {
        if (isEmpty(username)) {
            throw new IllegalArgumentException("username can't be null or empty");
        }
        if (isEmpty(password)) {
            throw new IllegalArgumentException("password can't be null or empty");
        }
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationRequest that = (AuthenticationRequest) o;

        if (!username.equals(that.username)) return false;
        return password.equals(that.password);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "username='" + username + '\'' +
                ", password='" + "xxxx" + '\'' +
                '}';
    }
}
