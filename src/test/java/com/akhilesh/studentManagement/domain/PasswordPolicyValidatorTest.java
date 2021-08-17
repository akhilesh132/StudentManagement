package com.akhilesh.studentManagement.domain;

import com.akhilesh.studentManagement.security.validators.PasswordPolicyValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PasswordPolicyValidatorTest {

    PasswordPolicyValidator validator = new PasswordPolicyValidator();

    @ParameterizedTest
    @ValueSource(strings = {
            "123",
            "12345",
            "1234567",
            "8765432",
            "#1235",
            "8765432jj"

    })
    void notAllowedPasswordTest(String password) {
        boolean isAllowed = validator.validate(password);
        assertFalse(isAllowed);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1237passwdU@",
            "123458dye783L@",
            "12345@6790d0dD",
            "8765432%jJ"
    })
    void allowedPasswordTest(String password) {
        boolean isAllowed = validator.validate(password);
       assertTrue(isAllowed);
    }

}