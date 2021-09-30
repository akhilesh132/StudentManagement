package com.akhilesh.hrms.security.services;

import com.akhilesh.hrms.security.domain.models.User;
import com.akhilesh.hrms.security.domain.models.Username;
import com.akhilesh.hrms.security.domain.services.UserService;

import com.akhilesh.hrms.security.persistence.repositories.concrete.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Username username = new Username(userName);
        Optional<User> byUsername = repository.findBy(username);
        if (byUsername.isEmpty()) throw new UsernameNotFoundException(userName);
        return byUsername.get().getUserDetails();
    }
}
