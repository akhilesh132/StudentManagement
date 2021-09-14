package com.akhilesh.hrms.security.persistence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.akhilesh.studentManagement.security.persistence.repositories.jpa"
)
public class PersistenceConfig {
}

