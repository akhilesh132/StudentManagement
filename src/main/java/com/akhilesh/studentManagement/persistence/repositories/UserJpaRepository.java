package com.akhilesh.studentManagement.persistence.repositories;

import com.akhilesh.studentManagement.persistence.entities.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserDTO, String> {
}
