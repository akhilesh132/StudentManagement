package com.akhilesh.hrms.security.domain.services;

import com.akhilesh.hrms.security.domain.models.User;
import com.akhilesh.hrms.security.domain.models.Username;

import java.util.Optional;

public interface UserService {

    void save(User user);

    Optional<User> findBy(Username username);
}
