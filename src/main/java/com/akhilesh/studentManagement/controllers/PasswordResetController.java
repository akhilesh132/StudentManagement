package com.akhilesh.studentManagement.controllers;

import com.akhilesh.studentManagement.domain.validators.PasswordPolicyValidator;
import com.akhilesh.studentManagement.domain.models.User;
import com.akhilesh.studentManagement.persistence.UserDTO;
import com.akhilesh.studentManagement.persistence.UserPasswordResetCodeDto;
import com.akhilesh.studentManagement.persistence.UserPasswordResetCodeRepository;
import com.akhilesh.studentManagement.persistence.UserRepository;
import com.akhilesh.studentManagement.request.models.PasswordResetRequest;
import com.akhilesh.studentManagement.request.models.PasswordRestTokenGenerationReq;
import org.apache.commons.lang3.RandomStringUtils;
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
    private final UserPasswordResetCodeRepository resetCodeRepository;
    private final UserRepository userRepository;
    private final PasswordPolicyValidator passwordPolicyValidator;

    @Autowired
    public PasswordResetController(JavaMailSender mailSender,
                                   UserPasswordResetCodeRepository resetCodeRepository,
                                   UserRepository userRepository,
                                   PasswordPolicyValidator passwordPolicyValidator) {
        this.mailSender = mailSender;
        this.resetCodeRepository = resetCodeRepository;
        this.userRepository = userRepository;
        this.passwordPolicyValidator = passwordPolicyValidator;
    }

    @PostMapping("/user/reset-password/generate-token")
    String generatePasswordResetToken(@Validated @RequestBody PasswordRestTokenGenerationReq req) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("rajakhil132@gmail.com");
        mail.setSubject("Password Reset Code");
        mail.setTo(req.getEmail());

        String secretCode = RandomStringUtils.randomAlphanumeric(4);
        mail.setText("code: " + secretCode);
        mailSender.send(mail);
        final UserPasswordResetCodeDto resetCodeDtodto = new UserPasswordResetCodeDto(req.getEmail(), secretCode);
        resetCodeRepository.save(resetCodeDtodto);

        return "password reset code has been sent over mail";
    }

    @PostMapping("/user/reset-password/reset")
    String resetPassword(@Validated @RequestBody PasswordResetRequest req) {
        String newPassword = req.getNewPassword();
        boolean isPasswordPolicyMet = passwordPolicyValidator.validate(newPassword);
        if (!isPasswordPolicyMet) {
            return "password policy not met, couldn't reset password";
        }

        String providedSecretCode = req.getSecretToken();
        final Optional<UserPasswordResetCodeDto> byId = resetCodeRepository.findById(req.getUserId());
        if (byId.isEmpty()) {
            return "user not found, couldn't reset password";
        }
        UserPasswordResetCodeDto resetCodeDto = byId.get();
        String generatedSecretCode = resetCodeDto.getSecretCode();
        if (generatedSecretCode.equals(providedSecretCode)) {
            User user = new User(req.getUserId(), newPassword);
            UserDTO updatedUser = new UserDTO(user);
            userRepository.save(updatedUser);
            return "password reset successful";
        }
        return "couldn't reset password, please check username and secret code";
    }
}
