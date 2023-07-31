package com.example.todo_list.web;


import com.example.todo_list.respositories.UserEntityRepository;
import com.example.todo_list.models.RegisterDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
