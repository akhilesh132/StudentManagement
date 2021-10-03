package com.akhilesh.hrms.security.persistence.repositories.concrete;

import com.akhilesh.hrms.security.domain.models.PasswordResetCode;
import com.akhilesh.hrms.security.domain.models.Secret;
import com.akhilesh.hrms.security.domain.models.User;
import com.akhilesh.hrms.security.domain.models.Username;
import com.akhilesh.hrms.security.persistence.entities.PasswordResetCodeDTO;
import com.akhilesh.hrms.security.persistence.repositories.jpa.PasswordResetCodeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PasswordResetCodeRepository {

    private final PasswordResetCodeJpaRepository jpaRepository;

    @Autowired
    public PasswordResetCodeRepository(PasswordResetCodeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public void save(PasswordResetCode resetCode) {
        String username = resetCode.getUsername().value();
        String rawSecret = resetCode.value();
        String encodedSecret = Secret.withValue(rawSecret).encoded().value();
        PasswordResetCodeDTO dto = new PasswordResetCodeDTO(username, encodedSecret);
        jpaRepository.save(dto);
    }

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
