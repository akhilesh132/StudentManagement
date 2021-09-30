package com.akhilesh.hrms.security.persistence.repositories.jpa;

import com.akhilesh.hrms.security.persistence.entities.PasswordResetCodeDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetCodeJpaRepository extends JpaRepository<PasswordResetCodeDTO,String> {
}
