package com.example.todo_list.services;

import com.example.todo_list.exceptions.UsernameAlreadyTakenException;
import com.example.todo_list.models.dto.RegisterDTO;
import com.example.todo_list.models.UserEntity;
import com.example.todo_list.respositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final UserEntityRepository userEntityRepository;

    public void registerUser(RegisterDTO registerDTO) {
        log.info("about to register user [{}]", registerDTO);
        if (userEntityRepository.existsUserEntityByUsername(registerDTO.username())) {
            throw new UsernameAlreadyTakenException();
        }
        UserEntity user = registerDTO.dtoToUserEntity(passwordEncoder);

        log.info("about to save new user [{}]", user);

        userEntityRepository.save(user);
    }
}
