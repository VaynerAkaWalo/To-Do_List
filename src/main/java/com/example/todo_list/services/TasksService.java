package com.example.todo_list.services;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.TaskDTO;
import com.example.todo_list.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface TasksService {

    void addTask(TaskDTO taskDTO, String username);

    void deleteTask(Long id, String username);

    void editTask(Long id, TaskDTO taskDTO, String username);

    void changeTaskStatus(Long id, String username);

    Optional<Task> getTaskById(Long id);

    List<Task> getAllTasksByUser(String username);
}
