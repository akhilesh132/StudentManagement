package com.akhilesh.hrms.security.services;

import com.akhilesh.hrms.security.persistence.entities.UserDTO;
import com.akhilesh.hrms.security.persistence.repositories.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDTO> byId = userJpaRepository.findById(username);
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
                .authorities("user")
                .build();

    }
}
