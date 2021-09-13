package com.akhilesh.hrms.security.persistence.repositories.jpa;

import com.akhilesh.hrms.security.persistence.entities.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserDTO, String> {
}
