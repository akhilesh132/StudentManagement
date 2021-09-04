package com.akhilesh.studentManagement.persistence.entities;

import com.akhilesh.studentManagement.security.domain.models.User;

import javax.persistence.*;
import java.util.UUID;

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
