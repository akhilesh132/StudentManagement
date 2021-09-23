package com.akhilesh.hrms.security.services;

import com.akhilesh.hrms.security.domain.models.Password;
import com.akhilesh.hrms.security.domain.models.User;
import com.akhilesh.hrms.security.domain.models.Username;
import com.akhilesh.hrms.security.persistence.entities.UserDTO;
import com.akhilesh.hrms.security.persistence.repositories.UserRepository;
import com.akhilesh.hrms.security.persistence.repositories.jpa.UserJpaRepository;
import com.akhilesh.hrms.security.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public Optional<User> findBy(Username username) {
        return repository.findBy(username);
    }
}
