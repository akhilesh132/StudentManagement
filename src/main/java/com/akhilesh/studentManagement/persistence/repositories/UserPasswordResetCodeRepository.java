package com.akhilesh.studentManagement.persistence.repositories;

import com.akhilesh.studentManagement.persistence.entities.UserPasswordResetCodeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordResetCodeRepository extends JpaRepository<UserPasswordResetCodeDto,String> {
}
