package com.akhilesh.studentManagement.security.domain.models;

import org.apache.commons.lang3.RandomStringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class SecretCode {
    @NotNull
    @NotEmpty
    private final String secretCode;

    public SecretCode() {
        this.secretCode = RandomStringUtils.randomAlphanumeric(4);
    }

    public static SecretCode withValue(String secretCode) {
        return new SecretCode(secretCode);
    }

    private SecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public String value() {
        return secretCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecretCode that = (SecretCode) o;

        return secretCode.equals(that.secretCode);
    }

    @Override
    public int hashCode() {
        return secretCode.hashCode();
    }

    @Override
    public String toString() {
        return "SecretCode{" +
                "secretCode='" + "xxxx" + '\'' +
                '}';
    }
}
