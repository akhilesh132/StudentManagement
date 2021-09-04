package com.akhilesh.studentManagement.security.controllers;

import com.akhilesh.studentManagement.security.controllers.models.response.GenericResponse;
import com.akhilesh.studentManagement.security.domain.exceptions.PasswordCriteriaException;
import com.akhilesh.studentManagement.security.domain.exceptions.UserNotFoundException;
import com.akhilesh.studentManagement.security.domain.models.*;
import com.akhilesh.studentManagement.persistence.repositories.PasswordResetCodeJpaRepository;
import com.akhilesh.studentManagement.security.controllers.models.request.PasswordResetReq;
import com.akhilesh.studentManagement.security.controllers.models.request.PasswordResetTokenGenerationReq;
import com.akhilesh.studentManagement.security.services.PasswordResetCodeRepository;
import com.akhilesh.studentManagement.security.services.UserRepository;
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

    UserRepository userRepository;
    private static final int TOKEN_TTL = 1;
    PasswordResetCodeRepository resetCodeRepository;

    @Autowired
    public PasswordResetController(JavaMailSender mailSender, PasswordResetCodeJpaRepository resetCodeRepository) {
        this.mailSender = mailSender;
    }

    @PostMapping("/user/password/reset/generate-token")
    String generatePasswordResetToken(@Validated @RequestBody PasswordResetTokenGenerationReq req)
            throws UserNotFoundException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("rajakhil132@gmail.com");
        mail.setSubject("Password Reset Code");
        mail.setTo(req.getEmail());

        String secretCode = new RandomSecret().value();
        mail.setText("code: " + secretCode);
        //mailSender.send(mail);
        Username username = new Username(req.getEmail());
        User user = userRepository.findBy(username);
        PasswordResetCode passwordResetCode = PasswordResetCode.forUser(user);
        resetCodeRepository.save(passwordResetCode);

        return "password reset code has been sent over mail";
    }

    @PostMapping("/user/reset-password/reset")
    String resetPassword(@Validated @RequestBody PasswordResetReq req)
            throws PasswordCriteriaException, UserNotFoundException {

        Password newPassword = new Password(req.getNewPassword());
        Username username = new Username(req.getUserId());

        User user = userRepository.findBy(username);
        PasswordResetCode passwordResetCode = resetCodeRepository.findForUser(user);
        boolean resetCodeExpired = passwordResetCode.isExpired();
        if (resetCodeExpired) {
            return "token expired, please generate password reset request again";
        }
        String providedSecretCode = req.getSecretToken();

        boolean isTokenValid = passwordResetCode.value().equals(providedSecretCode);
//        if (isTokenValid) {
//            User user = new User(userId, newPassword);
//            UserDTO updatedUser = new UserDTO(user);
//            userJpaRepository.save(updatedUser);
//            return "password reset successful";
//        }
        return null;
    }

    @ExceptionHandler
    public ResponseEntity<?> handlePasswordCriteriaException(PasswordCriteriaException pce) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GenericResponse("password policy not met, couldn't reset password"));
    }
}
