package com.example.todo_list.web.rest;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.dto.request.TaskCreationDTO;
import com.example.todo_list.models.dto.response.TaskDTO;
import com.example.todo_list.services.TasksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

        List<Task> tasks = tasksService.getAllTasksByUser(principal.getName());

        return  tasks.stream()
                .map(TaskDTO::from)
                .toList();
    }

    @GetMapping("/{taskId}")
    TaskDTO task(@PathVariable(value = "taskId") Long taskId, Principal principal) {
        log.info("about to retrieve task with id [{}] for user [{}]", taskId, principal.getName());

        Task task = tasksService.getTaskById(taskId, principal.getName());

        return TaskDTO.from(task);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TaskDTO createTask(@RequestBody TaskCreationDTO taskCreationDTO, Principal principal) {
        log.info("about to create task [{}] for user [{}]", taskCreationDTO, principal.getName());

        Task task = tasksService.addTask(taskCreationDTO, principal.getName());

        return TaskDTO.from(task);
    }

    @PutMapping("/{taskId}")
    TaskDTO updateTask(@PathVariable Long taskId, @RequestBody TaskCreationDTO taskCreationDTO, Principal principal) {
        log.info("about to update task [{}], new data [{}] for user [{}]", taskId, taskCreationDTO, principal.getName());

        Task task = tasksService.editTask(taskId, taskCreationDTO, principal.getName());

        return TaskDTO.from(task);
    }

    @PatchMapping("/{taskId}")
    TaskDTO toggleStatus(@PathVariable Long taskId, Principal principal) {
        log.info("about to toogle status for task [{}] by user [{}]", taskId, principal.getName());

        Task task = tasksService.changeTaskStatus(taskId, principal.getName());

        return TaskDTO.from(task);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTask(@PathVariable Long taskId, Principal principal) {
        log.info("about to delete task [{}] for user [{}]", taskId, principal.getName());

        tasksService.deleteTask(taskId, principal.getName());
    }
}
