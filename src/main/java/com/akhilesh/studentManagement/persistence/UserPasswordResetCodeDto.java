package com.akhilesh.studentManagement.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_password_reset_codes")
public class UserPasswordResetCodeDto extends Auditable<String>{

    private UserPasswordResetCodeDto() {
    }

    public UserPasswordResetCodeDto(String username, String secretCode) {
        this.username = username;
        this.secretCode = secretCode;
    }

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "secret_code")
    private String secretCode;

    public String getUsername() {
        return username;
    }

    public String getSecretCode() {
        return secretCode;
    }
}
