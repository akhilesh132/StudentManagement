package com.akhilesh.hrms.security.controllers.rest;


import com.akhilesh.hrms.security.controllers.models.request.AuthenticationRequest;
import com.akhilesh.hrms.security.controllers.models.response.AuthenticationResponse;
import com.akhilesh.hrms.security.domain.exceptions.UserNotFoundException;
import com.akhilesh.hrms.security.domain.models.JsonWebToken;
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
        User user = userService.findBy(username).orElseThrow(UserNotFoundException::new);

        JsonWebToken jwt = JsonWebToken.createFor(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthenticationResponse(jwt.value()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String badCredentialsExceptionHandler(BadCredentialsException e) {
        return "bad credentials";
    }

}
