package com.example.todo_list.services;


import com.example.todo_list.models.UserEntity;
import com.example.todo_list.respositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        log.info("about to retrieve user [{}]", username);
        return userEntityRepository.findByUsername(username);
    }
}
