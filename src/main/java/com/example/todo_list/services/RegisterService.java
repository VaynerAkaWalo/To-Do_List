package com.example.todo_list.services;

import com.example.todo_list.exceptions.UsernameAlreadyTakenException;
import com.example.todo_list.models.RegisterDTO;
import com.example.todo_list.respositories.UserEntityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    PasswordEncoder passwordEncoder;
    UserEntityRepository userEntityRepository;

    public RegisterService(PasswordEncoder passwordEncoder, UserEntityRepository userEntityRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userEntityRepository = userEntityRepository;
    }

    public void registerUser(RegisterDTO registerDTO) {
        if (userEntityRepository.existsUserEntityByUsername(registerDTO.username())) {
            throw new UsernameAlreadyTakenException();
        }
        userEntityRepository.save(registerDTO.dtoToUserEntity(passwordEncoder));
    }
}
