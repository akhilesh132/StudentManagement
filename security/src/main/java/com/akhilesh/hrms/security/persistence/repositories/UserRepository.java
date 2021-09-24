package com.akhilesh.hrms.security.persistence.repositories;

import com.akhilesh.hrms.security.domain.models.Password;
import com.akhilesh.hrms.security.domain.models.User;
import com.akhilesh.hrms.security.domain.models.Username;
import com.akhilesh.hrms.security.persistence.entities.UserDTO;
import com.akhilesh.hrms.security.persistence.repositories.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final UserJpaRepository jpaRepository;

    @Autowired
    public UserRepository(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public void save(User user) {
        String username = user.getUsername().value();
        String password = user.getPassword().value();
        UserDTO dto = new UserDTO(username, password);
        jpaRepository.save(dto);
    }


    public Optional<User> findBy(Username username) {
        String id = username.value();
        Optional<UserDTO> byId = jpaRepository.findById(id);
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        UserDTO dto = byId.get();
        Password password = new Password(dto.getPassword());
        User user = new User(username, password);
        return Optional.of(user);
    }
}
