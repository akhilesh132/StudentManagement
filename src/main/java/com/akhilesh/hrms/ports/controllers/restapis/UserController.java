package com.akhilesh.hrms.ports.controllers.restapis;

import com.akhilesh.hrms.security.persistence.repositories.jpa.UserJpaRepository;
import com.akhilesh.hrms.security.domain.exceptions.UserAlreadyExistsException;
import com.akhilesh.hrms.security.domain.models.Password;
import com.akhilesh.hrms.security.controllers.models.request.UserRegistrationRequest;
import com.akhilesh.hrms.security.controllers.models.response.GenericResponse;
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

    private final UserJpaRepository userJpaRepository;

    private static final String PASSWORD_CRITERIA_NOT_MET
            = "password doesn't meet acceptance criteria, user not created";
    private static final String USER_ALREADY_EXIST
            = "user already exists, user not created";
    private static final String USER_CREATED
            = "user created";

    @Autowired
    public UserController(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @PostMapping("/users")
    ResponseEntity<?> createUser(@Validated @RequestBody UserRegistrationRequest req)
           throws UserAlreadyExistsException {

        Password password = new Password(req.getPassword());

        boolean userAlreadyExists = userJpaRepository.existsById(req.getUsername());
        if (userAlreadyExists) throw new UserAlreadyExistsException();

        //userJpaRepository.save(new UserDTO(req));
        GenericResponse responseBody = new GenericResponse(USER_CREATED);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseBody);
    }

//    @ExceptionHandler
//    public ResponseEntity<?> handlePasswordCriteriaException(PasswordCriteriaException pce) {
//        GenericResponse responseBody = new GenericResponse(PASSWORD_CRITERIA_NOT_MET);
//        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                .body(responseBody);
//    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException uae) {
        GenericResponse responseBody = new GenericResponse(USER_ALREADY_EXIST);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(responseBody);
    }
}
