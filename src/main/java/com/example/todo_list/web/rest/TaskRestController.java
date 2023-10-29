package com.example.todo_list.web.rest;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.TaskDTO;
import com.example.todo_list.services.TasksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskRestController {

    private final TasksService tasksService;

    @GetMapping
    public ResponseEntity<?> task(@RequestParam(value = "taskId", required = false) Long taskId, Principal principal) {
        if (taskId == null) {
            log.info("about to retrieve all tasks for user [{}]", principal.getName());

            return ResponseEntity.ok(tasksService.getAllTasksByUser(principal.getName()));
        }
        log.info("about to retrieve task with id [{}] for user [{}]", taskId, principal.getName());

        return ResponseEntity.ok(tasksService.getTaskById(taskId, principal.getName()));
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO, Principal principal) {
        log.info("about to create task [{}] for user [{}]", taskDTO, principal.getName());

        Task result = tasksService.addTask(taskDTO, principal.getName());

        return ResponseEntity.created(URI.create("/api/tasks/" + result.getId())).build();
    }

    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(@RequestParam Long taskId, @RequestBody TaskDTO taskDTO, Principal principal) {
        log.info("about to update task [{}], new data [{}] for user [{}]", taskId, taskDTO, principal.getName());

        Task result = tasksService.editTask(taskId, taskDTO, principal.getName());

        return ResponseEntity.ok(TaskDTO.TaskToDTO(result));
    }

    @PatchMapping
    public ResponseEntity<TaskDTO> toggleStatus(@RequestParam Long taskId, Principal principal) {
        log.info("about to toogle status for task [{}] by user [{}]", taskId, principal.getName());

        Task result = tasksService.changeTaskStatus(taskId, principal.getName());

        return ResponseEntity.ok(TaskDTO.TaskToDTO(result));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestParam Long taskId, Principal principal) {
        log.info("about to delete task [{}] for user [{}]", taskId, principal.getName());

        tasksService.deleteTask(taskId, principal.getName());

        return ResponseEntity.noContent().build();
    }
}
