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
    public void addTask(TaskDTO taskDTO, String username) {
        Optional<UserEntity> userEntityOptional= userEntityRepository.findByUsername(username);
        UserEntity user = userEntityOptional.orElseThrow(UserNotFoundException::new);

        taskRepository.save(taskDTO.TaskDTOtoTask(user));
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
    public void editTask(Long id, TaskDTO taskDTO, String username) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(TaskNotFoundException::new);

        if (!task.getUserEntity().getUsername().equals(username)) {
            throw new UnauthorizedException();
        }

        task = taskDTO.TaskDTOtoTask(task.getUserEntity());
        task.setId(id);
        taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public void changeTaskStatus(Long id, String username) {
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
    }

    @Override
    public List<Task> getAllTasksByUser(String username) {
        Optional<UserEntity> userEntityOptional= userEntityRepository.findByUsername(username);
        UserEntity user = userEntityOptional.orElseThrow(UserNotFoundException::new);

        return taskRepository.getAllByUserEntity(user);
    }

}
