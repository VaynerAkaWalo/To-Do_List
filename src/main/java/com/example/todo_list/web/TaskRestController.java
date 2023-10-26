package com.example.todo_list.web;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.TaskDTO;
import com.example.todo_list.services.TasksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    private final TasksService tasksService;

    public TaskRestController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public ResponseEntity<?> task(@RequestParam(required = false) Long id, Principal principal) {
        if (id == null) {
            return ResponseEntity.ok(tasksService.getAllTasksByUser(principal.getName()));
        }
        return ResponseEntity.ok(tasksService.getTaskById(id, principal.getName()));
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO, Principal principal) {
        Task result = tasksService.addTask(taskDTO, principal.getName());
        return ResponseEntity.created(URI.create("/task/" + result.getId())).build();
    }

    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(@RequestParam Long id, @RequestBody TaskDTO taskDTO, Principal principal) {
        Task result = tasksService.editTask(id, taskDTO, principal.getName());
        return ResponseEntity.ok(TaskDTO.TaskToDTO(result));
    }

    @PatchMapping
    public ResponseEntity<TaskDTO> toggleStatus(@RequestParam Long id, Principal principal) {
        Task result = tasksService.changeTaskStatus(id, principal.getName());
        return ResponseEntity.ok(TaskDTO.TaskToDTO(result));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestParam Long id, Principal principal) {
        tasksService.deleteTask(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}
