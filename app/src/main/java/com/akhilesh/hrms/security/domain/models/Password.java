package com.akhilesh.hrms.security.domain.models;

import com.akhilesh.hrms.security.domain.exceptions.PasswordCriteriaException;
import org.passay.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class Password {

    private static final PasswordCriteriaValidator criteriaValidator = new PasswordCriteriaValidator();
    @NotNull
    @NotEmpty
    private final String value;

    public Password(String password) {
        if (password == null || password.isBlank() || password.isEmpty())
            throw new IllegalArgumentException("password can't be empty or null");
        if (criteriaValidator.isCriteriaViolated(password)) {
            throw new PasswordCriteriaException();
        }
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

    private static class PasswordCriteriaValidator {
        private final char[] illegalCharacters = new char[]{'#'};

        public boolean isCriteriaViolated(String password) {
            if (password == null || password.isEmpty()) return false;

            PasswordData passwordData = new PasswordData(password);
            PasswordValidator passwordValidator = new PasswordValidator(
                    new LengthRule(8, 16),
                    new IllegalCharacterRule(illegalCharacters),
                    new CharacterRule(EnglishCharacterData.UpperCase, 1),
                    new CharacterRule(EnglishCharacterData.LowerCase, 1),
                    new CharacterRule(EnglishCharacterData.Digit, 1),
                    new CharacterRule(EnglishCharacterData.Special, 1)
            );
            RuleResult result = passwordValidator.validate(passwordData);
            return !result.isValid();
        }

    }
}
