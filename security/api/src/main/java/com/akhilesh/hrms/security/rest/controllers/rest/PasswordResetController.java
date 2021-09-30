package com.akhilesh.hrms.security.rest.controllers.rest;

import com.akhilesh.hrms.security.rest.controllers.models.request.PasswordResetRequest;
import com.akhilesh.hrms.security.rest.controllers.models.request.PasswordResetTokenRequest;
import com.akhilesh.hrms.security.rest.controllers.models.response.GenericResponse;
import com.akhilesh.hrms.security.domain.exceptions.PasswordCriteriaException;
import com.akhilesh.hrms.security.domain.exceptions.PasswordResetCodeExpiredException;
import com.akhilesh.hrms.security.domain.exceptions.PasswordResetCodeNotFoundException;
import com.akhilesh.hrms.security.domain.exceptions.UserNotFoundException;
import com.akhilesh.hrms.security.domain.models.Password;
import com.akhilesh.hrms.security.domain.models.PasswordResetCode;
import com.akhilesh.hrms.security.domain.models.User;
import com.akhilesh.hrms.security.domain.models.Username;
import com.akhilesh.hrms.security.domain.services.PasswordResetCodeService;
import com.akhilesh.hrms.security.domain.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PasswordResetController {

    private final JavaMailSender mailSender;
    private final UserService userService;
    private final PasswordResetCodeService codeService;

    @Autowired
    public PasswordResetController(JavaMailSender mailSender,
                                   UserService userService,
                                   PasswordResetCodeService codeService) {
        this.mailSender = mailSender;
        this.userService = userService;
        this.codeService = codeService;
    }

    @PostMapping("/user/password/reset/generate-token")
    String generatePasswordResetToken(@Validated @RequestBody PasswordResetTokenRequest request) {

        Username username = new Username(request.getUsername());
        Optional<User> byUsername = userService.findBy(username);
        if (byUsername.isEmpty()) throw new UserNotFoundException(username);
        User user = byUsername.get();

        PasswordResetCode passwordResetCode = PasswordResetCode.forUser(user);
        String secretCode = passwordResetCode.value();
        codeService.save(passwordResetCode);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("rajakhil132@gmail.com");
        mail.setSubject("Password Reset Code");
        mail.setTo(request.getUsername());
        mail.setText("code: " + secretCode);
        //mailSender.send(mail);

        return "password reset code has been sent over mail: " + secretCode;
    }

    @PostMapping("/user/password/reset/password-reset")
    ResponseEntity<?> resetPassword(@Validated @RequestBody PasswordResetRequest request) {

        Password newPassword = new Password(request.getPassword());
        Username username = new Username(request.getUsername());
        Optional<User> byUsername = userService.findBy(username);
        if (byUsername.isEmpty()) throw new UserNotFoundException(username);
        User user = byUsername.get();

        PasswordResetCode passwordResetCode = codeService.findForUser(user)
                .orElseThrow(PasswordResetCodeNotFoundException::new);

        boolean resetCodeIsExpired = passwordResetCode.isExpired();
        if (resetCodeIsExpired) {
            throw new PasswordResetCodeExpiredException();
        }

        String token = request.getToken();
        boolean tokenIsValid = passwordResetCode.matches(token);
        if (!tokenIsValid) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new GenericResponse("invalid secret token, couldn't reset password"));
        }

        User updatedUser = user.withPassword(newPassword);
        userService.save(updatedUser);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GenericResponse("password reset successful"));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleWeakPasswords(PasswordCriteriaException pce) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new GenericResponse("password doesn't meet password criteria"));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new GenericResponse("username not found"));
    }

    @ExceptionHandler
    public ResponseEntity<?> handlePasswordResetCodeNotExists(PasswordResetCodeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new GenericResponse("password couldn't reset, retry"));
    }

    @ExceptionHandler
    public ResponseEntity<?> handlePasswordResetCodeExpired(PasswordResetCodeExpiredException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new GenericResponse("token expired, please generate password reset request again"));
    }
}
