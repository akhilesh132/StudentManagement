package com.akhilesh.studentManagement.ports.controllers;

import com.akhilesh.studentManagement.domain.models.User;
import com.akhilesh.studentManagement.persistence.UserDTO;
import com.akhilesh.studentManagement.persistence.UserRepository;
import com.akhilesh.studentManagement.ports.models.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {

    private final UserRepository userRepository;

    private static final String INVALID_USERNAME_OR_PASSWORD = "invalid username or password";
    private static final String LOGIN_SUCCESS_MESSAGE = "logged in";

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@Validated @RequestBody User user) {
        Optional<UserDTO> byId = userRepository.findById(user.getUserId());

        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new GenericResponse(INVALID_USERNAME_OR_PASSWORD));
        }

        UserDTO userDTO = byId.get();
        if (userDTO.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new GenericResponse(LOGIN_SUCCESS_MESSAGE));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GenericResponse(INVALID_USERNAME_OR_PASSWORD));
    }
}
