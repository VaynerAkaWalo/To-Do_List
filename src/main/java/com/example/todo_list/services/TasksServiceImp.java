package com.example.todo_list.services;

import com.example.todo_list.respositories.TaskRepository;
import com.example.todo_list.respositories.UserEntityRepository;
import com.example.todo_list.models.Task;
import com.example.todo_list.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TasksServiceImp implements TasksService {

    private TaskRepository taskRepository;
    private UserEntityRepository userEntityRepository;

    public TasksServiceImp(TaskRepository taskRepository, UserEntityRepository userEntityRepository) {
        this.taskRepository = taskRepository;
        this.userEntityRepository = userEntityRepository;
    }

    public List<Task> getUserTasks(String username) {
        if (username == null) {
            return new ArrayList<>();
        }

        UserEntity user = userEntityRepository.findByUsername(username);

        return taskRepository.getAllByUserEntity(user);
    }


}
