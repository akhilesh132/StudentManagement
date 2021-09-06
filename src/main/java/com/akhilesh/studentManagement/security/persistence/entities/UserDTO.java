package com.akhilesh.studentManagement.security.persistence.entities;

import com.akhilesh.studentManagement.security.domain.models.User;
import com.akhilesh.studentManagement.security.persistence.audit.AbstractAuditable;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDTO extends AbstractAuditable<String> {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    private UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
