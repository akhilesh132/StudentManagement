package com.akhilesh.studentManagement.security.persistence.repositories;

import com.akhilesh.studentManagement.security.persistence.entities.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserDTO, String> {
}
