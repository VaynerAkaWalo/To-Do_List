package com.example.todo_list.models.dto;

import com.example.todo_list.models.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public record RegisterDTO(String username, String password) {

    public UserEntity dtoToUserEntity (PasswordEncoder passwordEncoder) {
        return new UserEntity(username, passwordEncoder.encode(password));
    }
}
