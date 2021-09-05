package com.akhilesh.studentManagement.security.persistence.entities;

import com.akhilesh.studentManagement.security.domain.models.User;
import com.akhilesh.studentManagement.security.persistence.audit.AbstractAuditable;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDTO extends AbstractAuditable<String> {

    @Id
    @GeneratedValue
    @Column(name = "userId")
    private String userId;
    @Column(name = "password")
    private String password;

    private UserDTO() {
    }

    public UserDTO(User user) {


    }

    public String getUserId() {
        return this.userId;
    }

    public String getPassword() {
        return password;
    }
}
