package com.akhilesh.studentManagement.security.controllers;


import com.akhilesh.studentManagement.security.request.models.AuthenticationRequest;
import com.akhilesh.studentManagement.security.response.models.AuthenticationResponse;
import com.akhilesh.studentManagement.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    private JwtUtils jwtUtils = new JwtUtils();

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Validated AuthenticationRequest req) {
        final String userId = req.getUserId();
        final String password = req.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        final String jwtToken = jwtUtils.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthenticationResponse(jwtToken));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String badCredentialsExceptionHandler(BadCredentialsException e) {
        return "bad credentials";
    }

}
