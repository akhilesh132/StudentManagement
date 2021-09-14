package com.akhilesh.hrms.security.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_password_reset_codes")
public class PasswordResetCodeDTO {

    @Id
    @Column(name = "username", nullable = false)
    @NotNull
    @NotEmpty
    private String username;

    @Column(name = "secret_code", nullable = false)
    @NotNull
    @NotEmpty
    private String secretCode;

    @NotNull
    @NotEmpty
    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generationDate;

    private PasswordResetCodeDTO() {
    }

    public PasswordResetCodeDTO(String username, String secretCode) {
        this.generationDate = LocalDateTime.now();
        this.username = username;
        this.secretCode = secretCode;
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
