package com.akhilesh.studentManagement.security.controllers.rest;

import com.akhilesh.studentManagement.security.controllers.models.response.GenericResponse;
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

import java.util.Optional;

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
    String generatePasswordResetToken(@Validated @RequestBody PasswordResetTokenRequest req) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("rajakhil132@gmail.com");
        mail.setSubject("Password Reset Code");
        mail.setTo(req.getUsername());

        String secretCode = new SecretCode().value();
        mail.setText("code: " + secretCode);
        //mailSender.send(mail);
        Username username = new Username(req.getUsername());
        Optional<User> user = userService.findBy(username);
        PasswordResetCode passwordResetCode = PasswordResetCode.forUser(user.get());
        resetCodeRepository.save(passwordResetCode);

        return "password reset code has been sent over mail";
    }

    @PostMapping("/user/password/reset/password-reset")
    String resetPassword(@Validated @RequestBody PasswordResetRequest req) {

        Password newPassword = new Password(req.getUpdatedPassword());
        Username username = new Username(req.getUsername());

        Optional<User> user = userService.findBy(username);
        Optional<PasswordResetCode> byUsername = resetCodeRepository.findForUser(user.get());
        if (byUsername.isEmpty()) {
            return "no password reset token exists";
        }
        PasswordResetCode passwordResetCode = byUsername.get();

        boolean resetCodeIsExpired = passwordResetCode.isExpired();
        if (resetCodeIsExpired) {
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

//    @ExceptionHandler
//    public ResponseEntity<?> handlePasswordCriteriaException(PasswordCriteriaException pce) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new GenericResponse("password policy not met, couldn't reset password"));
//    }
}
