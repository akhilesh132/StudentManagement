package com.akhilesh.studentManagement.security.domain.models;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomSecret {

    private final String secretCode;

    public RandomSecret() {
        this.secretCode = RandomStringUtils.randomAlphanumeric(4);
    }

    public String value() {
        return secretCode;
    }

}
