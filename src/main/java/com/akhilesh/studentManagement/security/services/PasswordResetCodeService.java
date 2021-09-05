package com.akhilesh.studentManagement.security.services;

import com.akhilesh.studentManagement.security.domain.models.PasswordResetCode;
import com.akhilesh.studentManagement.security.domain.models.User;

import java.util.Optional;

public interface PasswordResetCodeService {

    void save(PasswordResetCode passwordResetCode);

    Optional<PasswordResetCode> findForUser(User user);
}
