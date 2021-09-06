package com.akhilesh.studentManagement.security.services;

import com.akhilesh.studentManagement.security.domain.models.User;
import com.akhilesh.studentManagement.security.domain.models.Username;

import java.util.Optional;

public interface UserService {

    void save(User user);

    Optional<User> findBy(Username username);
}
