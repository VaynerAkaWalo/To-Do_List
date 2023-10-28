package com.example.todo_list.web.ui;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.TaskDTO;
import com.example.todo_list.services.TasksService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/task")
public class TaskController {

    TasksService tasksService;

    public TaskController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public String taskView(@RequestParam Long id, Model model, Principal principal) {
        Task task = tasksService.getTaskById(id, principal.getName());

        if(task != null) {
            model.addAttribute(task);
            return "task";
        }
        return "redirect:/";
    }

    @ModelAttribute(name = "task")
    public Task task() { return new Task(); }

    @GetMapping("/create")
    public String createTaskView() {
        return "newTask";
    }

    @PostMapping("/create")
    public String createTask(TaskDTO taskDTO, Principal principal) {
        tasksService.addTask(taskDTO, principal.getName());

        return "redirect:/";
    }
}

