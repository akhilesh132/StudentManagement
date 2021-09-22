package com.akhilesh.hrms.security.beans;

import com.akhilesh.hrms.security.domain.services.UserService;
import com.akhilesh.hrms.security.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsServiceBean {

    @Autowired
    UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userService);
    }
}
