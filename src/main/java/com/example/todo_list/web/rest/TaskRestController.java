package com.example.todo_list.web.rest;

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
    public ResponseEntity<?> task(@RequestParam(value = "taskId", required = false) Long taskId, Principal principal) {
        if (taskId == null) {
            return ResponseEntity.ok(tasksService.getAllTasksByUser(principal.getName()));
        }
        return ResponseEntity.ok(tasksService.getTaskById(taskId, principal.getName()));
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO, Principal principal) {
        Task result = tasksService.addTask(taskDTO, principal.getName());
        return ResponseEntity.created(URI.create("/api/tasks/" + result.getId())).build();
    }

    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(@RequestParam Long taskId, @RequestBody TaskDTO taskDTO, Principal principal) {
        Task result = tasksService.editTask(taskId, taskDTO, principal.getName());
        return ResponseEntity.ok(TaskDTO.TaskToDTO(result));
    }

    @PatchMapping
    public ResponseEntity<TaskDTO> toggleStatus(@RequestParam Long taskId, Principal principal) {
        Task result = tasksService.changeTaskStatus(taskId, principal.getName());
        return ResponseEntity.ok(TaskDTO.TaskToDTO(result));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestParam Long taskId, Principal principal) {
        tasksService.deleteTask(taskId, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
