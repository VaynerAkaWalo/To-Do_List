package com.example.todo_list.web;


import com.example.todo_list.models.RegisterDTO;
import com.example.todo_list.services.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class  RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping
    public String registerForm() {
        return "register";
    }

    @PostMapping
    public String registerPost(RegisterDTO registerDTO, Model model) {
        try {
            registerService.registerUser(registerDTO);
        }
        catch (RuntimeException exception) {
            model.addAttribute("message", "Username already taken");
            return "register";
        }

        return "redirect:/login";
    }

    @ModelAttribute
    public String message() {
        return "";
    }
}
