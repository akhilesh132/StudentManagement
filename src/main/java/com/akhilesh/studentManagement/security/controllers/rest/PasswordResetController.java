package com.akhilesh.studentManagement.security.controllers.rest;

import com.akhilesh.studentManagement.security.controllers.models.response.GenericResponse;
import com.akhilesh.studentManagement.security.domain.exceptions.PasswordCriteriaException;
import com.akhilesh.studentManagement.security.domain.exceptions.UserNotFoundException;
import com.akhilesh.studentManagement.security.domain.models.*;
import com.akhilesh.studentManagement.security.controllers.models.request.PasswordResetRequest;
import com.akhilesh.studentManagement.security.controllers.models.request.PasswordResetTokenRequest;
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
    private final UserRepository userRepository;
    private final PasswordResetCodeRepository resetCodeRepository;

    @Autowired
    public PasswordResetController(JavaMailSender mailSender,
                                   UserRepository userRepository,
                                   PasswordResetCodeRepository resetCodeRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.resetCodeRepository = resetCodeRepository;
    }

    @PostMapping("/user/password/reset/generate-token")
    String generatePasswordResetToken(@Validated @RequestBody PasswordResetTokenRequest req)
            throws UserNotFoundException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("rajakhil132@gmail.com");
        mail.setSubject("Password Reset Code");
        mail.setTo(req.getUsername());

        String secretCode = new RandomSecret().value();
        mail.setText("code: " + secretCode);
        //mailSender.send(mail);
        Username username = new Username(req.getUsername());
        User user = userRepository.findBy(username);
        PasswordResetCode passwordResetCode = PasswordResetCode.forUser(user);
        resetCodeRepository.save(passwordResetCode);

        return "password reset code has been sent over mail";
    }

    @PostMapping("/user/reset-password/reset")
    String resetPassword(@Validated @RequestBody PasswordResetRequest req)
            throws PasswordCriteriaException, UserNotFoundException {

        Password newPassword = new Password(req.getUpdatedPassword());
        Username username = new Username(req.getUsername());

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
