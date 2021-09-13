package com.akhilesh.hrms.security.persistence.entities;

import com.akhilesh.hrms.security.persistence.audit.AbstractAuditable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDTO extends AbstractAuditable {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    private UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = passwordEncoder.encode(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
