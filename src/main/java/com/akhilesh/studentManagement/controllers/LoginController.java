package com.akhilesh.studentManagement.controllers;

import com.akhilesh.studentManagement.domain.models.User;
import com.akhilesh.studentManagement.persistence.UserDTO;
import com.akhilesh.studentManagement.persistence.UserRepository;
import com.akhilesh.studentManagement.response.models.GenericResponse;
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

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@Validated @RequestBody User user) {
        Optional<UserDTO> byId = userRepository.findById(user.getUserId());
        if (byId.isEmpty()) {
            return new ResponseEntity<>(new GenericResponse(INVALID_USERNAME_OR_PASSWORD), HttpStatus.OK);
        }
        UserDTO userDTO = byId.get();
        if (userDTO.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>(new GenericResponse("logged in"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new GenericResponse(INVALID_USERNAME_OR_PASSWORD), HttpStatus.OK);
    }
}
