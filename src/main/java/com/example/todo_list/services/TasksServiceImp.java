package com.example.todo_list.services;

import com.example.todo_list.exceptions.TaskNotFoundException;
import com.example.todo_list.exceptions.UnauthorizedException;
import com.example.todo_list.exceptions.UserNotFoundException;
import com.example.todo_list.models.TaskDTO;
import com.example.todo_list.models.TaskStatus;
import com.example.todo_list.respositories.TaskRepository;
import com.example.todo_list.respositories.UserEntityRepository;
import com.example.todo_list.models.Task;
import com.example.todo_list.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TasksServiceImp implements TasksService {

    private final TaskRepository taskRepository;
    private final UserEntityRepository userEntityRepository;

    public TasksServiceImp(TaskRepository taskRepository, UserEntityRepository userEntityRepository) {
        this.taskRepository = taskRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public Task addTask(TaskDTO taskDTO, String username) {
        Optional<UserEntity> userEntityOptional= userEntityRepository.findByUsername(username);
        UserEntity user = userEntityOptional.orElseThrow(UserNotFoundException::new);

        Task task = new Task();
        task.setUserEntity(user);

        taskDTO.transferDataToTask(task);

        taskRepository.save(task);

        return task;
    }

    @Override
    public void deleteTask(Long id, String username) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(TaskNotFoundException::new);

        if (!task.getUserEntity().getUsername().equals(username)) {
            throw new UnauthorizedException();
        }

        taskRepository.deleteById(id);
    }

    @Override
    public Task editTask(Long id, TaskDTO taskDTO, String username) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(TaskNotFoundException::new);

        if (!task.getUserEntity().getUsername().equals(username)) {
            throw new UnauthorizedException();
        }

        taskDTO.transferDataToTask(task);

        taskRepository.save(task);

        return task;
    }

    @Override
    public Task getTaskById(Long id, String username) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(TaskNotFoundException::new);

        if (!task.getUserEntity().getUsername().equals(username)) {
            throw new UnauthorizedException();
        }

        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public Task changeTaskStatus(Long id, String username) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(TaskNotFoundException::new);

        if (!task.getUserEntity().getUsername().equals(username)) {
            throw new UnauthorizedException();
        }

        if (task.getStatus() == TaskStatus.COMPLETED) {
            task.setAsUncompleted();
        }
        else {
            task.setAsCompleted();
        }

        taskRepository.save(task);

        return task;
    }

    @Override
    public List<Task> getAllTasksByUser(String username) {
        Optional<UserEntity> userEntityOptional= userEntityRepository.findByUsername(username);
        UserEntity user = userEntityOptional.orElseThrow(UserNotFoundException::new);

        return taskRepository.getAllByUserEntity(user);
    }

}
