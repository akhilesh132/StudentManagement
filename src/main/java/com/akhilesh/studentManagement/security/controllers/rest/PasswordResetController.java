package com.akhilesh.studentManagement.security.controllers.rest;

import com.akhilesh.studentManagement.security.domain.models.*;
import com.akhilesh.studentManagement.security.controllers.models.request.PasswordResetRequest;
import com.akhilesh.studentManagement.security.controllers.models.request.PasswordResetTokenRequest;
import com.akhilesh.studentManagement.security.services.PasswordResetCodeService;
import com.akhilesh.studentManagement.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
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
    String generatePasswordResetToken(@Validated @RequestBody PasswordResetTokenRequest request) {

        Username username = new Username(request.getUsername());
        Optional<User> byUsername = userService.findBy(username);
        if (byUsername.isEmpty()) {
            return "user not found";
        }
        User user = byUsername.get();

        PasswordResetCode passwordResetCode = PasswordResetCode.forUser(user);
        String secretCode = passwordResetCode.value();
        resetCodeRepository.save(passwordResetCode);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("rajakhil132@gmail.com");
        mail.setSubject("Password Reset Code");
        mail.setTo(request.getUsername());
        mail.setText("code: " + secretCode);
        //mailSender.send(mail);

        return "password reset code has been sent over mail: "+ secretCode;
    }

    @PostMapping("/user/password/reset/password-reset")
    String resetPassword(@Validated @RequestBody PasswordResetRequest request) {

        Password newPassword = new Password(request.getPassword());
        if (newPassword.violatesPasswordCriteria()) {
            return "password doesn't meet password criteria";
        }

        Username username = new Username(request.getUsername());
        Optional<User> byUsername = userService.findBy(username);
        if (byUsername.isEmpty()) {
            return "user not found";
        }
        User user = byUsername.get();

        Optional<PasswordResetCode> byUser = resetCodeRepository.findForUser(user);
        if (byUser.isEmpty()) {
            return "no password reset token exists";
        }
        PasswordResetCode passwordResetCode = byUser.get();

        boolean resetCodeIsExpired = passwordResetCode.isExpired();
        if (resetCodeIsExpired) {
            return "token expired, please generate password reset request again";
        }

        String token = request.getToken();
        Secret secret = Secret.withValue(token);
        boolean isTokenValid = passwordResetCode.matches(secret);

        if (isTokenValid) {
            User updatedUser = user.withPassword(newPassword);
            userService.save(updatedUser);
        }
        return null;
    }

//    @ExceptionHandler
//    public ResponseEntity<?> handlePasswordCriteriaException(PasswordCriteriaException pce) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new GenericResponse("password policy not met, couldn't reset password"));
//    }
}
