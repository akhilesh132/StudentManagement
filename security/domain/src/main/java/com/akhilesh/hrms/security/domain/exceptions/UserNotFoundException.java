package com.akhilesh.hrms.security.domain.exceptions;

import com.akhilesh.hrms.security.domain.models.Username;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends UsernameNotFoundException {

    public UserNotFoundException(Username username) {
        super(username.value() + "not found");
    }
}
