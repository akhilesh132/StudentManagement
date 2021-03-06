package com.akhilesh.hrms.security.domain.services;

import com.akhilesh.hrms.security.domain.models.PasswordResetCode;
import com.akhilesh.hrms.security.domain.models.User;

import java.util.Optional;

public interface PasswordResetCodeService {
    void save(PasswordResetCode resetCode);

    Optional<PasswordResetCode> findForUser(User user);
}
