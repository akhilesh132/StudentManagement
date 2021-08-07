package com.akhilesh.studentManagement.domain.validators;

import org.passay.*;
import org.springframework.stereotype.Component;

@Component
public class PasswordPolicyValidator {
    private final char[] illegalCharacters = new char[]{'#'};

    public boolean validate(String password) {
        if (password==null || password.isEmpty()) return false;

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
        return result.isValid();
    }

}
