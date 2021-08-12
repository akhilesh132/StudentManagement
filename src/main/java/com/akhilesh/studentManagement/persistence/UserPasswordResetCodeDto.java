package com.akhilesh.studentManagement.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_password_reset_codes")
public class UserPasswordResetCodeDto extends Auditable<String> {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "secret_code")
    private String secretCode;

    @Column(name = "generated_at")
    private LocalDateTime generationDate;

    private UserPasswordResetCodeDto() {
    }

    public UserPasswordResetCodeDto(String username, String secretCode) {
        this.username = username;
        this.secretCode = secretCode;
        this.generationDate = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public LocalDateTime getGenerationDate() {
        return generationDate;
    }
}
