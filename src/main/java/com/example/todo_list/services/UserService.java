package com.example.todo_list.services;

import com.example.todo_list.models.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getUserByUsername(String username);
}
