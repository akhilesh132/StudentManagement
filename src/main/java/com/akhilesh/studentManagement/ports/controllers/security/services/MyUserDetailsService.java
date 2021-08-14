package com.akhilesh.studentManagement.ports.controllers.security.services;

import com.akhilesh.studentManagement.persistence.entities.UserDTO;
import com.akhilesh.studentManagement.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDTO> byId = userRepository.findById(username);
        if (byId.isEmpty()) {
            throw new UsernameNotFoundException("user not found with user id: " + username);
        }

        UserDTO userDTO = byId.get();
        return User.builder()
                .username(username)
                .password(userDTO.getPassword())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();

    }
}
