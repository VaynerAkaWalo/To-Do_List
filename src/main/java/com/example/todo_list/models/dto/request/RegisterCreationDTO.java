package com.example.todo_list.models.dto.request;

import com.example.todo_list.models.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public record RegisterCreationDTO(String username, String password) {

    public UserEntity to(PasswordEncoder passwordEncoder) {
        return new UserEntity(username, passwordEncoder.encode(password));
    }
}
