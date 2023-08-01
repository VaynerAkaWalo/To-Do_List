package com.example.todo_list.services;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.TaskDTO;
import com.example.todo_list.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface TasksService {

    Task addTask(TaskDTO taskDTO, String username);

    void deleteTask(Long id, String username);

    Task editTask(Long id, TaskDTO taskDTO, String username);

    Task changeTaskStatus(Long id, String username);

    Task getTaskById(Long id, String username);

    List<Task> getAllTasksByUser(String username);
}
