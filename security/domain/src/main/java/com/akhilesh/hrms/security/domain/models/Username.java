package com.akhilesh.hrms.security.domain.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public final class Username {
    @NotNull
    @NotEmpty
    private final String username;

    public Username(String username) {
        if (isEmpty(username)) {
            throw new IllegalArgumentException("username can't be null or empty");
        }
        this.username = username;
    }

    public String value() {
        return this.username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Username username1 = (Username) o;

        return username.equals(username1.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "Username{" +
                "username='" + username + '\'' +
                '}';
    }
}
