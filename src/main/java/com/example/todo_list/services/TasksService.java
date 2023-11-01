package com.example.todo_list.services;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.dto.TaskDTO;

import java.util.List;

public interface TasksService {

    Task addTask(TaskDTO taskDTO, String username);

    void deleteTask(Long id, String username);

    Task editTask(Long id, TaskDTO taskDTO, String username);

    Task changeTaskStatus(Long id, String username);

    Task getTaskById(Long id, String username);

    List<Task> getAllTasksByUser(String username);
}
