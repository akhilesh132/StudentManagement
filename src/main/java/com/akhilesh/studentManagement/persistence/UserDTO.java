package com.akhilesh.studentManagement.persistence;

import com.akhilesh.studentManagement.domain.models.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDTO {

    @Id
    @Column(name = "username")
    private  String username;
    @Column(name = "password")
    private  String password;

    private UserDTO() {
    }

    public UserDTO(User user)  {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public String getUserId(){
        return this.username;
    }

    public String getUserEmail(){
        return this.username;
    }

    public String getPassword() {
        return password;
    }
}
