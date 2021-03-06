package com.akhilesh.hrms.security.beans;


import com.akhilesh.hrms.security.persistence.repositories.concrete.UserRepository;
import com.akhilesh.hrms.security.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsServiceBean {

    @Autowired
    UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl(userRepository);
    }
}
