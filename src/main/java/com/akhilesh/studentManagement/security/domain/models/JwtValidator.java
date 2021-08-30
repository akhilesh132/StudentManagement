package com.akhilesh.studentManagement.security.domain.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtValidator {

    private final JwtExtractor jwtExtractor;

    @Autowired
    public JwtValidator(JwtExtractor jwtExtractor) {
        this.jwtExtractor = jwtExtractor;
    }

    public boolean validate(Jwt authToken, UserDetails userDetails) {
         String jwt = authToken.value();
         String username = jwtExtractor.extractUsername(jwt);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String token) {
        return jwtExtractor.extractExpiration(token).before(new Date());
    }
}
