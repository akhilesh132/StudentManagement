package com.akhilesh.studentManagement.ports.controllers.restapis;

import com.akhilesh.studentManagement.security.domain.exceptions.PasswordCriteriaException;
import com.akhilesh.studentManagement.security.domain.models.Password;
import com.akhilesh.studentManagement.security.validators.PasswordCriteriaValidator;
import com.akhilesh.studentManagement.persistence.repositories.UserRepository;
import com.akhilesh.studentManagement.ports.models.request.UserRegistrationReq;
import com.akhilesh.studentManagement.ports.models.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final PasswordCriteriaValidator passwordPolicyValidator;
    private final UserRepository userRepository;

    private static final String PASSWORD_CRITERIA_NOT_MET_MESSAGE
            = "password doesn't meet acceptance criteria, user not created";
    private static final String USER_ALREADY_EXIST_MESSAGE
            = "user already exists, user not created";
    private static final String USER_CREATED_MESSAGE
            = "user created";

    @Autowired
    public UserController(PasswordCriteriaValidator passwordPolicyValidator, UserRepository userRepository) {
        this.passwordPolicyValidator = passwordPolicyValidator;
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    ResponseEntity<GenericResponse> createUser(@Validated @RequestBody UserRegistrationReq req)
            throws PasswordCriteriaException {

        Password password = new Password(req.getPassword());

        boolean userAlreadyExists = userRepository.existsById(req.getEmailId());
        if (userAlreadyExists) {
            GenericResponse responseBody = new GenericResponse(USER_ALREADY_EXIST_MESSAGE);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(responseBody);
        }

        //userRepository.save(new UserDTO(req));
        GenericResponse responseBody = new GenericResponse(USER_CREATED_MESSAGE);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseBody);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlePasswordCriteriaException(PasswordCriteriaException pce){
        GenericResponse responseBody = new GenericResponse(PASSWORD_CRITERIA_NOT_MET_MESSAGE);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(responseBody);
    }
}
