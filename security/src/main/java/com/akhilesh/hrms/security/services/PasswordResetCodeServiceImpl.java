package com.akhilesh.hrms.security.services;

import com.akhilesh.hrms.security.domain.models.PasswordResetCode;
import com.akhilesh.hrms.security.domain.models.Secret;
import com.akhilesh.hrms.security.domain.models.User;
import com.akhilesh.hrms.security.domain.models.Username;
import com.akhilesh.hrms.security.domain.services.PasswordResetCodeService;
import com.akhilesh.hrms.security.persistence.repositories.PasswordResetCodeRepository;
import com.akhilesh.hrms.security.persistence.repositories.jpa.PasswordResetCodeJpaRepository;
import com.akhilesh.hrms.security.persistence.entities.PasswordResetCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class PasswordResetCodeServiceImpl implements PasswordResetCodeService {

    private final PasswordResetCodeRepository repository;

    @Autowired
    public PasswordResetCodeServiceImpl(PasswordResetCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(PasswordResetCode resetCode) {
        repository.save(resetCode);
    }

    @Override
    public Optional<PasswordResetCode> findForUser(User user) {
        return repository.findForUser(user);
    }
}
