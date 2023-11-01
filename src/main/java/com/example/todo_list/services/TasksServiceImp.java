package com.example.todo_list.services;

import com.example.todo_list.exceptions.TaskNotFoundException;
import com.example.todo_list.exceptions.UnauthorizedException;
import com.example.todo_list.exceptions.UserNotFoundException;
import com.example.todo_list.models.Task;
import com.example.todo_list.models.dto.TaskDTO;
import com.example.todo_list.models.TaskStatus;
import com.example.todo_list.models.UserEntity;
import com.example.todo_list.respositories.TaskRepository;
import com.example.todo_list.respositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TasksServiceImp implements TasksService {

    private final TaskRepository taskRepository;
    private final UserEntityRepository userEntityRepository;


    @Override
    public Task addTask(TaskDTO taskDTO, String username) {
        log.info("about to retrieve user [{}]", username);
        Optional<UserEntity> userEntityOptional= userEntityRepository.findByUsername(username);
        UserEntity user = userEntityOptional.orElseThrow(UserNotFoundException::new);

        Task task = new Task();
        task.setUserEntity(user);

        taskDTO.transferDataToTask(task);

        log.info("about to save task [{}] by user [{}]", task, user.getUsername());
        taskRepository.save(task);

        return task;
    }

    @Override
    public void deleteTask(Long id, String username) {
        log.info("about to retrieve task [{}] by user [{}]", id, username);
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(TaskNotFoundException::new);

        if (!task.getUserEntity().getUsername().equals(username)) {
            log.info("user [{}] tried to delete task [{}] which belongs to user [{}]",
                    username,
                    id,
                    task.getUserEntity().getUsername());
            throw new UnauthorizedException();
        }

        log.info("about to delete task [{}] by user [{}]", id, username);
        taskRepository.deleteById(id);
    }

    @Override
    public Task editTask(Long id, TaskDTO taskDTO, String username) {
        log.info("about to retrieve task [{}] by user [{}]", id, username);
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(TaskNotFoundException::new);

        if (!task.getUserEntity().getUsername().equals(username)) {
            log.info("user [{}] tried to update task [{}] which belongs to user [{}]",
                    username,
                    id,
                    task.getUserEntity().getUsername());
            throw new UnauthorizedException();
        }

        taskDTO.transferDataToTask(task);

        log.info("about to update task [{}] by user [{}]", id, username);
        taskRepository.save(task);

        return task;
    }

    @Override
    public Task getTaskById(Long id, String username) {
        log.info("about to retrieve task [{}] by user [{}]", id, username);
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(TaskNotFoundException::new);

        if (!task.getUserEntity().getUsername().equals(username)) {
            log.info("user [{}] tried to get task [{}] which belongs to user [{}]",
                    username,
                    id,
                    task.getUserEntity().getUsername());
            throw new UnauthorizedException();
        }

        return task;
    }

    @Override
    public Task changeTaskStatus(Long id, String username) {
        log.info("about to retrieve task [{}] by user [{}]", id, username);
        Optional<Task> taskOptional = taskRepository.findById(id);
        Task task = taskOptional.orElseThrow(TaskNotFoundException::new);

        if (!task.getUserEntity().getUsername().equals(username)) {
            log.info("user [{}] tried to update status of task [{}] which belongs to user [{}]",
                    username,
                    id,
                    task.getUserEntity().getUsername());
            throw new UnauthorizedException();
        }

        if (task.getStatus() == TaskStatus.COMPLETED) {
            task.setAsUncompleted();
        }
        else {
            task.setAsCompleted();
        }

        log.info("about to update status of task [{}] by user [{}]", id, username);
        taskRepository.save(task);

        return task;
    }

    @Override
    public List<Task> getAllTasksByUser(String username) {
        log.info("about to retrieve all task by user [{}]", username);
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByUsername(username);
        UserEntity user = userEntityOptional.orElseThrow(UserNotFoundException::new);

        return taskRepository.getAllByUserEntity(user);
    }

}
