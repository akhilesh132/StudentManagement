package com.akhilesh.studentManagement.security.persistence.repositories;

import com.akhilesh.studentManagement.security.domain.models.Password;
import com.akhilesh.studentManagement.security.domain.models.User;
import com.akhilesh.studentManagement.security.domain.models.Username;
import com.akhilesh.studentManagement.security.persistence.entities.UserDTO;
import com.akhilesh.studentManagement.security.persistence.repositories.jpa.UserJpaRepository;
import com.akhilesh.studentManagement.security.services.UserService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepository implements UserService {

    private UserJpaRepository jpaRepository;

    @Override
    public void save(User user) {
        String username = user.getUsername().value();
        String password = user.getPassword().value();
        UserDTO dto = new UserDTO(username, password);
        jpaRepository.save(dto);
    }

    @Override
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
