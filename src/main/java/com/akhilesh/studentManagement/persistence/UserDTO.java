package com.akhilesh.studentManagement.persistence;

import com.akhilesh.studentManagement.domain.models.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDTO extends Auditable<String>{

    @Id
    @Column(name = "userId")
    private  String userId;
    @Column(name = "password")
    private  String password;

    private UserDTO() {
    }

    public UserDTO(User user)  {
        this.userId = user.getUserId();
        this.password = user.getPassword();
    }

    public String getUserId(){
        return this.userId;
    }

    public String getPassword() {
        return password;
    }
}
