package com.example.todo_list.web;


import com.example.todo_list.data.UserEntityRepository;
import com.example.todo_list.models.RegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/register")
public class  RegisterController {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "register";
    }

    @PostMapping
    public String registerPost(RegisterDTO registerDTO) {
        if (userEntityRepository.existsUserEntityByUsername(registerDTO.username())){
            return "register";
        }

        userEntityRepository.save(registerDTO.dtoToUserEntity(passwordEncoder));
        return "redirect:/login";
    }
}
