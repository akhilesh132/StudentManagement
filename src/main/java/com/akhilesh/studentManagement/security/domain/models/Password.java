package com.akhilesh.studentManagement.security.domain.models;

import com.akhilesh.studentManagement.security.domain.exceptions.PasswordCriteriaException;
import com.akhilesh.studentManagement.security.validators.PasswordCriteriaValidator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class Password {

    private static final PasswordCriteriaValidator criteriaValidator = new PasswordCriteriaValidator();
    @NotNull
    @NotEmpty
    private final String value;

    public Password(String password) throws PasswordCriteriaException {
        if (password == null || password.isBlank() || password.isEmpty())
            throw new IllegalArgumentException("password can't be empty or null");

        boolean passwordCriteriaNotMet = !criteriaValidator.validate(password);
        if (passwordCriteriaNotMet) throw new PasswordCriteriaException();

        this.value = password;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password = (Password) o;

        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "Password{" +
                "value='" + "xxxx" + '\'' +
                '}';
    }
}
