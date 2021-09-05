package com.akhilesh.studentManagement.security.persistence.repositories;

import com.akhilesh.studentManagement.security.persistence.entities.UserPasswordResetCodeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetCodeJpaRepository extends JpaRepository<UserPasswordResetCodeDto,String> {
}
