package com.example.todo_list.web.rest;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.dto.TaskDTO;
import com.example.todo_list.services.TasksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskRestController {

    private final TasksService tasksService;

    @GetMapping
    List<TaskDTO> tasks(Principal principal) {
        log.info("about to retrieve all tasks for user [{}]", principal.getName());

        return tasksService.getAllTasksByUser(principal.getName())
                .stream()
                .map(TaskDTO::from)
                .toList();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> task(@PathVariable(value = "taskId") Long taskId, Principal principal) {
        log.info("about to retrieve task with id [{}] for user [{}]", taskId, principal.getName());

        return ResponseEntity.ok(tasksService.getTaskById(taskId, principal.getName()));
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO, Principal principal) {
        log.info("about to create task [{}] for user [{}]", taskDTO, principal.getName());

        Task result = tasksService.addTask(taskDTO, principal.getName());

        return ResponseEntity.created(URI.create("/api/tasks/" + result.getId())).build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO, Principal principal) {
        log.info("about to update task [{}], new data [{}] for user [{}]", taskId, taskDTO, principal.getName());

        Task result = tasksService.editTask(taskId, taskDTO, principal.getName());

        return ResponseEntity.ok(TaskDTO.from(result));
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskDTO> toggleStatus(@PathVariable Long taskId, Principal principal) {
        log.info("about to toogle status for task [{}] by user [{}]", taskId, principal.getName());

        Task result = tasksService.changeTaskStatus(taskId, principal.getName());

        return ResponseEntity.ok(TaskDTO.from(result));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, Principal principal) {
        log.info("about to delete task [{}] for user [{}]", taskId, principal.getName());

        tasksService.deleteTask(taskId, principal.getName());

        return ResponseEntity.noContent().build();
    }
}
