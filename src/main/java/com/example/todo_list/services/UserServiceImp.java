package com.example.todo_list.services;


import com.example.todo_list.models.UserEntity;
import com.example.todo_list.respositories.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserEntityRepository userEntityRepository;

    public UserServiceImp(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }
}
