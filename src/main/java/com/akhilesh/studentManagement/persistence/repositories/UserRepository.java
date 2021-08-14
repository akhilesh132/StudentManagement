package com.akhilesh.studentManagement.persistence.repositories;

import com.akhilesh.studentManagement.persistence.entities.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDTO, String> {
}
