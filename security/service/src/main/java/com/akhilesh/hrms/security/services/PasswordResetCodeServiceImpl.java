package com.akhilesh.hrms.security.services;

import com.akhilesh.hrms.security.domain.models.PasswordResetCode;
import com.akhilesh.hrms.security.domain.models.User;
import com.akhilesh.hrms.security.domain.services.PasswordResetCodeService;

import com.akhilesh.hrms.security.persistence.repositories.concrete.PasswordResetCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
