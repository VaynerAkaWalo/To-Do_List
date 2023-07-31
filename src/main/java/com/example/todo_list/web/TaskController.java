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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserEntityRepository userEntityRepository;

    public TaskController(TaskRepository taskRepository, UserEntityRepository userEntityRepository) {
        this.taskRepository = taskRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @GetMapping
    public String taskView(@RequestParam Long id, Model model) {
        Optional<Task> task = taskRepository.findById(id);
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
    public String createTask(TaskDTO taskDTO, Model model, Principal principal) {
        Task task = (Task) model.getAttribute("task");
        if (task != null) {
            UserEntity user = userEntityRepository.findByUsername(principal.getName());
            if (user != null) {
                task.setUserEntity(user);
                taskDTO.TaskDTOtoTask(task);
                taskRepository.save(task);
                return "redirect:/";
            }
            throw new InternalErrorException();
        }
        throw new NullPointerException();
    }

    @PutMapping
    public ResponseEntity<String> updateTask(@RequestParam Long id,@RequestBody Task requestTask, Principal principal) {
        Optional<Task> current = taskRepository.findById(id);
        if (current.isPresent()) {
            Task updated = current.get();
            if (updated.getUserEntity().getUsername().equals(principal.getName())) {
                updated.setName(requestTask.getName());
                updated.setDetails(requestTask.getDetails());
                taskRepository.save(updated);
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            }
            return new ResponseEntity<>("It's not your task", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }

    @PatchMapping
    public ResponseEntity<String> changeStatus(@RequestParam Long id, Principal principal) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            if (task.getUserEntity().getUsername().equals(principal.getName())) {
                if (task.getStatus().equals(TaskStatus.COMPLETED)) {
                    task.setAsUncompleted();
                }
                else {
                    task.setAsCompleted();
                }

                taskRepository.save(task);
                return new ResponseEntity<>("Status changed", HttpStatus.OK);
            }

            return new ResponseEntity<>("It's not your task", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTask(@RequestParam Long id, Principal principal) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            if (task.getUserEntity().getUsername().equals(principal.getName())) {
                taskRepository.delete(task);
                return new ResponseEntity<>("Task deleted", HttpStatus.OK);
            }

            throw new UnauthorizedException("User: " + principal.getName() +", tried to delete not his task with id:" + id);
        }
        throw  new TaskNotFoundException("Not found task with id: " + id);
    }
}

