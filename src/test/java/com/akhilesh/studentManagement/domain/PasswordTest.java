package com.akhilesh.studentManagement.domain;

import com.akhilesh.studentManagement.security.domain.models.Password;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.core.parameters.P;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "123",
            "12345",
            "1234567",
            "8765432",
            "#1235",
            "8765432jj"

    })
    void notAllowedPasswordTest(String value) {
        Password password = new Password(value);
        assertFalse(password.isCriteriaMet());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1237passwdU@",
            "123458dye783L@",
            "12345@6790d0dD",
            "8765432%jJ"
    })
    void allowedPasswordTest(String value) {
        Password password = new Password(value);
        assertTrue(password.isCriteriaMet());
    }

}