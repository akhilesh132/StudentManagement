package com.akhilesh.studentManagement.security.controllers.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRegistrationReq {
    @NotNull
    @NotBlank
    private final String emailId;
    @NotNull
    @NotBlank
    @Pattern(regexp = "[0-9]{10}")
    private final String mobile;
    @NotNull
    @NotBlank
    private final String password;

    public UserRegistrationReq(String emailId, String mobileNumber, String password) {
        this.emailId = emailId;
        this.mobile = mobileNumber;
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getMobileNumber() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }
}


