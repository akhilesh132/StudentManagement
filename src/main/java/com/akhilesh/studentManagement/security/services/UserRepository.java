package com.akhilesh.studentManagement.security.services;

import com.akhilesh.studentManagement.security.domain.exceptions.UserNotFoundException;
import com.akhilesh.studentManagement.security.domain.models.User;
import com.akhilesh.studentManagement.security.domain.models.Username;

public interface UserRepository {

    void save(User user);

    User findBy(Username username) throws UserNotFoundException;
}
