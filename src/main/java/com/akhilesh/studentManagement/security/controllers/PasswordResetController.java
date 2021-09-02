package com.akhilesh.studentManagement.security.controllers;

import com.akhilesh.studentManagement.ports.models.response.GenericResponse;
import com.akhilesh.studentManagement.security.domain.exceptions.PasswordCriteriaException;
import com.akhilesh.studentManagement.security.domain.models.Password;
import com.akhilesh.studentManagement.security.domain.models.RandomSecret;
import com.akhilesh.studentManagement.security.validators.PasswordCriteriaValidator;
import com.akhilesh.studentManagement.domain.models.User;
import com.akhilesh.studentManagement.persistence.entities.UserDTO;
import com.akhilesh.studentManagement.persistence.entities.UserPasswordResetCodeDto;
import com.akhilesh.studentManagement.persistence.repositories.UserPasswordResetCodeRepository;
import com.akhilesh.studentManagement.persistence.repositories.UserRepository;
import com.akhilesh.studentManagement.ports.models.request.PasswordResetReq;
import com.akhilesh.studentManagement.ports.models.request.PasswordResetTokenGenerationReq;
import org.apache.commons.lang3.RandomStringUtils;
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

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class PasswordResetController {

    private final JavaMailSender mailSender;
    private final UserPasswordResetCodeRepository resetCodeRepository;
    private final UserRepository userRepository;

    private static final int TOKEN_TTL = 1;

    @Autowired
    public PasswordResetController(JavaMailSender mailSender,
                                   UserPasswordResetCodeRepository resetCodeRepository,
                                   UserRepository userRepository) {
        this.mailSender = mailSender;
        this.resetCodeRepository = resetCodeRepository;
        this.userRepository = userRepository;

    }

    @PostMapping("/user/password/reset/generate-token")
    String generatePasswordResetToken(@Validated @RequestBody PasswordResetTokenGenerationReq req) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("rajakhil132@gmail.com");
        mail.setSubject("Password Reset Code");
        mail.setTo(req.getEmail());

        String secretCode = new RandomSecret().value();
        mail.setText("code: " + secretCode);
        //mailSender.send(mail);
        UserPasswordResetCodeDto resetCodeDto = new UserPasswordResetCodeDto(req.getEmail(), secretCode);
        resetCodeRepository.save(resetCodeDto);

        return "password reset code has been sent over mail";
    }

    @PostMapping("/user/reset-password/reset")
    String resetPassword(@Validated @RequestBody PasswordResetReq req) throws PasswordCriteriaException {
        Password newPassword = new Password(req.getNewPassword());

        String userId = req.getUserId();
        String providedSecretCode = req.getSecretToken();
        Optional<UserPasswordResetCodeDto> byId = resetCodeRepository.findById(userId);
        if (byId.isEmpty()) {
            return "user not found, couldn't reset password";
        }

        UserPasswordResetCodeDto resetCodeDto = byId.get();
        LocalDateTime tokenGenDate = resetCodeDto.getGenerationDate();
        LocalDateTime now = LocalDateTime.now();
        boolean isExpired = tokenGenDate.plusMinutes(TOKEN_TTL).isBefore(now);
        if (isExpired) {
            return "token expired, please generate password reset request again";
        }

        String generatedSecretCode = resetCodeDto.getSecretCode();
        boolean isTokenValid = generatedSecretCode.equals(providedSecretCode);
        if (isTokenValid) {
            User user = new User(userId, newPassword);
            UserDTO updatedUser = new UserDTO(user);
            userRepository.save(updatedUser);
            return "password reset successful";
        }
        return null;
    }

    @ExceptionHandler
    public ResponseEntity<?> handlePasswordCriteriaException(PasswordCriteriaException pce) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GenericResponse("password policy not met, couldn't reset password"));
    }
}
