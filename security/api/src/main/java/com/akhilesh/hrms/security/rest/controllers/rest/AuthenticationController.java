package com.akhilesh.hrms.security.rest.controllers.rest;


import com.akhilesh.hrms.security.rest.controllers.models.request.AuthenticationRequest;
import com.akhilesh.hrms.security.rest.controllers.models.response.AuthenticationResponse;
import com.akhilesh.hrms.security.domain.exceptions.UserNotFoundException;
import com.akhilesh.hrms.security.domain.models.AccessToken;
import com.akhilesh.hrms.security.domain.models.User;
import com.akhilesh.hrms.security.domain.models.Username;
import com.akhilesh.hrms.security.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Validated AuthenticationRequest request) {

        Username username = new Username(request.getUsername());
        String password = request.getPassword();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        authenticationManager.authenticate(authToken);

        Optional<User> byUsername = userService.findBy(username);
        if (byUsername.isEmpty()) throw new UserNotFoundException(username);
        User user = byUsername.get();

        AccessToken accessToken = AccessToken.createFor(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthenticationResponse(accessToken.value()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String badCredentialsExceptionHandler(BadCredentialsException e) {
        return "bad credentials";
    }

}
