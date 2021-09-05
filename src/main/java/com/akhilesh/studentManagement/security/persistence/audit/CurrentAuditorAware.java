package com.akhilesh.studentManagement.security.persistence.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class CurrentAuditorAware<T> implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("akm");
    }
}
