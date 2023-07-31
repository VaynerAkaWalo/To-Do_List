package com.example.todo_list.web;

import com.example.todo_list.services.TasksService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {

    private final TasksService tasksService;

    public HomeController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @ModelAttribute
    public void getTasks(Model model, Principal principal) {
        model.addAttribute("tasks", tasksService.getAllTasksByUser(principal.getName()));
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }
}
