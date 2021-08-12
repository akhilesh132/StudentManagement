package com.akhilesh.studentManagement.ports.models.request;

public class UserRegistrationReq {

    private final String emailId;
    private final String mobile;

    public UserRegistrationReq(String emailId, String mobileNumber) {
        this.emailId = emailId;
        this.mobile = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getMobileNumber() {
        return mobile;
    }
}


