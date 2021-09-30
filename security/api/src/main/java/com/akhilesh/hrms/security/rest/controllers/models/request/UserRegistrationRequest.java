package com.akhilesh.hrms.security.rest.controllers.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRegistrationRequest {
    @NotNull
    @NotBlank
    private final String username;
    @NotNull
    @NotBlank
    @Pattern(regexp = "[0-9]{10}")
    private final String mobile;
    @NotNull
    @NotBlank
    private final String password;

    public UserRegistrationRequest(String username, String mobileNumber, String password) {
        this.username = username;
        this.mobile = mobileNumber;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getMobileNumber() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }
}


