package com.akhilesh.studentManagement.security.persistence.repositories.jpa;

import com.akhilesh.studentManagement.security.persistence.entities.PasswordResetCodeDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetCodeJpaRepository extends JpaRepository<PasswordResetCodeDTO,String> {
}
