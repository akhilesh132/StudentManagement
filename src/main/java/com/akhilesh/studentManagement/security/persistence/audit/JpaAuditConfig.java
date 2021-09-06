package com.akhilesh.studentManagement.security.persistence.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "userAware")
public class JpaAuditConfig {

    @Bean
    public AuditorAware<String> userAware() {
        return new SpringSecurityAuditorAware();
    }
}
