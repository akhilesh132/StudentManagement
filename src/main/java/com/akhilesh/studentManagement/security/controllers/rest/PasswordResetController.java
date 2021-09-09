package com.akhilesh.studentManagement.security.controllers.rest;

import com.akhilesh.studentManagement.security.controllers.models.response.GenericResponse;
import com.akhilesh.studentManagement.security.domain.exceptions.PasswordCriteriaException;
import com.akhilesh.studentManagement.security.domain.exceptions.PasswordResetCodeExpiredException;
import com.akhilesh.studentManagement.security.domain.exceptions.PasswordResetCodeNotFoundException;
import com.akhilesh.studentManagement.security.domain.exceptions.UserNotFoundException;
import com.akhilesh.studentManagement.security.domain.models.*;
import com.akhilesh.studentManagement.security.controllers.models.request.PasswordResetRequest;
import com.akhilesh.studentManagement.security.controllers.models.request.PasswordResetTokenRequest;
import com.akhilesh.studentManagement.security.services.PasswordResetCodeService;
import com.akhilesh.studentManagement.security.services.UserService;
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

@RestController
public class PasswordResetController {

    private final JavaMailSender mailSender;
    private final UserService userService;
    private final PasswordResetCodeService resetCodeRepository;

    @Autowired
    public PasswordResetController(JavaMailSender mailSender,
                                   UserService userService,
                                   PasswordResetCodeService resetCodeRepository) {
        this.mailSender = mailSender;
        this.userService = userService;
        this.resetCodeRepository = resetCodeRepository;
    }

    @PostMapping("/user/password/reset/generate-token")
    String generatePasswordResetToken(@Validated @RequestBody PasswordResetTokenRequest request) {

        Username username = new Username(request.getUsername());
        User user = userService.findBy(username).orElseThrow(UserNotFoundException::new);

        PasswordResetCode passwordResetCode = PasswordResetCode.forUser(user);
        String secretCode = passwordResetCode.value();
        resetCodeRepository.save(passwordResetCode);

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
        User user = userService.findBy(username)
                .orElseThrow(UserNotFoundException::new);
        PasswordResetCode passwordResetCode = resetCodeRepository.findForUser(user)
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
