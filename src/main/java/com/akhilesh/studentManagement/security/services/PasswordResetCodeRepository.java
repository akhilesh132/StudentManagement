package com.akhilesh.studentManagement.security.services;

import com.akhilesh.studentManagement.security.domain.models.PasswordResetCode;
import com.akhilesh.studentManagement.security.domain.models.User;

public interface PasswordResetCodeRepository {
    void save(PasswordResetCode passwordResetCode);

    PasswordResetCode findForUser(User user);
}
