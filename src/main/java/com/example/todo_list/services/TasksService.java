package com.example.todo_list.services;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.dto.request.TaskCreationDTO;

import java.util.List;

public interface TasksService {

    Task addTask(TaskCreationDTO taskCreationDTO, String username);

    void deleteTask(Long id, String username);

    Task editTask(Long id, TaskCreationDTO taskCreationDTO, String username);

    Task changeTaskStatus(Long id, String username);

    Task getTaskById(Long id, String username);

    List<Task> getAllTasksByUser(String username);
}
