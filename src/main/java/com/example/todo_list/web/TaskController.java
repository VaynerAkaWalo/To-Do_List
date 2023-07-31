package com.example.todo_list.web;

import com.example.todo_list.respositories.TaskRepository;
import com.example.todo_list.respositories.UserEntityRepository;
import com.example.todo_list.exceptions.InternalErrorException;
import com.example.todo_list.exceptions.TaskNotFoundException;
import com.example.todo_list.exceptions.UnauthorizedException;
import com.example.todo_list.models.Task;
import com.example.todo_list.models.TaskDTO;
import com.example.todo_list.models.TaskStatus;
import com.example.todo_list.models.UserEntity;
import com.example.todo_list.services.TasksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/task")
public class TaskController {

    TasksService tasksService;

    public TaskController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public String taskView(@RequestParam Long id, Model model) {
        Optional<Task> task = tasksService.getTaskById(id);

        if(task.isPresent()) {
            model.addAttribute(task.get());
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
    public String createTask(@RequestBody TaskDTO taskDTO, Principal principal) {
        tasksService.addTask(taskDTO, principal.getName());

        return "redirect:/";
    }

    @PutMapping
    public void updateTask(@RequestParam Long id,@RequestBody TaskDTO taskDTO, Principal principal) {
        tasksService.editTask(id, taskDTO, principal.getName());
    }

    @PatchMapping
    public void changeStatus(@RequestParam Long id, Principal principal) {
        tasksService.changeTaskStatus(id, principal.getName());
    }

    @DeleteMapping
    public void deleteTask(@RequestParam Long id, Principal principal) {
        tasksService.deleteTask(id, principal.getName());
    }
}

