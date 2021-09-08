package com.akhilesh.studentManagement.security.persistence.repositories;

import com.akhilesh.studentManagement.security.domain.models.PasswordResetCode;
import com.akhilesh.studentManagement.security.domain.models.Secret;
import com.akhilesh.studentManagement.security.domain.models.User;
import com.akhilesh.studentManagement.security.domain.models.Username;
import com.akhilesh.studentManagement.security.persistence.entities.PasswordResetCodeDTO;
import com.akhilesh.studentManagement.security.persistence.repositories.jpa.PasswordResetCodeJpaRepository;
import com.akhilesh.studentManagement.security.services.PasswordResetCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class PasswordResetCodeRepository implements PasswordResetCodeService {

    private final PasswordResetCodeJpaRepository jpaRepository;

    @Autowired
    public PasswordResetCodeRepository(PasswordResetCodeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(PasswordResetCode resetCode) {
        String username = resetCode.getUsername().value();
        String rawSecret = resetCode.value();
        String encodedSecret = Secret.withValue(rawSecret).encoded().value();
        PasswordResetCodeDTO dto = new PasswordResetCodeDTO(username, encodedSecret);
        jpaRepository.save(dto);
    }

    @Override
    public Optional<PasswordResetCode> findForUser(User user) {
        Username username = user.getUsername();
        Optional<PasswordResetCodeDTO> byId = jpaRepository.findById(username.value());
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        PasswordResetCodeDTO dto = byId.get();
        String encodedSecret = dto.getSecretCode();
        Secret secret = Secret.withValue(encodedSecret);
        PasswordResetCode resetCode = new PasswordResetCode(username, secret);
        return Optional.of(resetCode);
    }
}
