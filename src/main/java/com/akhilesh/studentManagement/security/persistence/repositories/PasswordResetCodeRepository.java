package com.akhilesh.studentManagement.security.persistence.repositories;

import com.akhilesh.studentManagement.security.domain.models.PasswordResetCode;
import com.akhilesh.studentManagement.security.domain.models.SecretCode;
import com.akhilesh.studentManagement.security.domain.models.User;
import com.akhilesh.studentManagement.security.domain.models.Username;
import com.akhilesh.studentManagement.security.persistence.entities.PasswordResetCodeDTO;
import com.akhilesh.studentManagement.security.persistence.repositories.jpa.PasswordResetCodeJpaRepository;
import com.akhilesh.studentManagement.security.services.PasswordResetCodeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public final class PasswordResetCodeRepository implements PasswordResetCodeService {

    private final PasswordResetCodeJpaRepository jpaRepository;

    @Autowired
    public PasswordResetCodeRepository(PasswordResetCodeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(PasswordResetCode resetCode) {
        String username = resetCode.getUsername().value();
        String secretCode = resetCode.value();
        PasswordResetCodeDTO resetCodeDTO = new PasswordResetCodeDTO(username, secretCode);
        jpaRepository.save(resetCodeDTO);
    }

    @Override
    public Optional<PasswordResetCode> findForUser(User user) {
        Username username = user.getUsername();
        Optional<PasswordResetCodeDTO> byId = jpaRepository.findById(username.value());
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        PasswordResetCodeDTO resetCodeDTO = byId.get();
        SecretCode secretCode = SecretCode.withValue(resetCodeDTO.getSecretCode());
        return Optional.of(new PasswordResetCode(username, secretCode));
    }
}
