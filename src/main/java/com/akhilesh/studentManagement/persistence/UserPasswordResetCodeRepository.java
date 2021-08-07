package com.akhilesh.studentManagement.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordResetCodeRepository extends JpaRepository<UserPasswordResetCodeDto,String> {
}
