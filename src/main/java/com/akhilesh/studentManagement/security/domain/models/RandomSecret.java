package com.akhilesh.studentManagement.security.domain.models;

import org.apache.commons.lang3.RandomStringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class RandomSecret {
    @NotNull
    @NotEmpty
    private final String secretCode;

    public RandomSecret() {
        this.secretCode = RandomStringUtils.randomAlphanumeric(4);
    }

    public String value() {
        return secretCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RandomSecret that = (RandomSecret) o;

        return secretCode.equals(that.secretCode);
    }

    @Override
    public int hashCode() {
        return secretCode.hashCode();
    }

    @Override
    public String toString() {
        return "RandomSecret{" +
                "secretCode='" + "xxxx" + '\'' +
                '}';
    }
}
