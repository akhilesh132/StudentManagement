package com.akhilesh.hrms.security.domain.models;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class Secret {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private final String secret;

    public Secret() {
        this.secret = RandomStringUtils.randomAlphanumeric(4);
    }

    public static Secret withValue(String rawValue) {
        return new Secret(rawValue);
    }

    private Secret(String rawValue) {
        this.secret = rawValue;
    }

    public Secret encoded() {
        return new Secret(encoder.encode(this.secret));
    }

    public boolean matches(Secret provided) {
        return encoder.matches(provided.value(), this.secret);
    }

    public boolean matches(String provided) {
        return encoder.matches(provided, this.secret);
    }

    public String value() {
        return secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Secret that = (Secret) o;

        return secret.equals(that.secret);
    }

    @Override
    public int hashCode() {
        return secret.hashCode();
    }

    @Override
    public String toString() {
        return "Secret{" +
                "secretCode='" + "xxxx" + '\'' +
                '}';
    }


}
