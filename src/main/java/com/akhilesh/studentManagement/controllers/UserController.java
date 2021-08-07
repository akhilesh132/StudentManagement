package com.akhilesh.studentManagement.controllers;

import com.akhilesh.studentManagement.domain.validators.PasswordPolicyValidator;
import com.akhilesh.studentManagement.domain.models.User;
import com.akhilesh.studentManagement.persistence.UserDTO;
import com.akhilesh.studentManagement.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
public class UserController {

    private final PasswordPolicyValidator passwordPolicyValidator;
    private final UserRepository userRepository;

    private static final String PASSWORD_CRITERIA_NOT_MET_MESSAGE
            = "password doesn't meet acceptance criteria, user not created";
    private static final String USER_ALREADY_EXIST_MESSAGE
            = "user already exists, user not created";
    private static final String USER_CREATED_MESSAGE
            = "user created";

    @Autowired
    public UserController(PasswordPolicyValidator passwordPolicyValidator, UserRepository userRepository) {
        this.passwordPolicyValidator = passwordPolicyValidator;
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    ResponseEntity<UserCreationRequestResponse> createUser(@Validated @RequestBody User s) {

        String password = s.getPassword();
        boolean isPasswdStrengthAcceptable = passwordPolicyValidator.validate(password);
        if (!isPasswdStrengthAcceptable) {
            UserCreationRequestResponse responseBody = new UserCreationRequestResponse(PASSWORD_CRITERIA_NOT_MET_MESSAGE);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
        }

        boolean userAlreadyExists = userRepository.existsById(s.getUserId());
        if (userAlreadyExists) {
            UserCreationRequestResponse responseBody = new UserCreationRequestResponse(USER_ALREADY_EXIST_MESSAGE);
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }

        userRepository.save(new UserDTO(s));
        UserCreationRequestResponse responseBody = new UserCreationRequestResponse(USER_CREATED_MESSAGE);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }


    @ExceptionHandler({Exception.class})
    String errorHandler() {
        return "something went wrong !!";
    }

    private static class UserCreationRequestResponse{
         private final String message;
         private final ZonedDateTime timestamp;

        public UserCreationRequestResponse(String message) {
            this.message = message;
            this.timestamp = ZonedDateTime.now();
        }

        public String getMessage() {
            return message;
        }

        public ZonedDateTime getTimestamp() {
            return timestamp;
        }
    }

}
