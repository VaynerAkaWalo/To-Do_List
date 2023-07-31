package com.example.todo_list;

import com.example.todo_list.respositories.TaskRepository;
import com.example.todo_list.respositories.UserEntityRepository;
import com.example.todo_list.models.Task;
import com.example.todo_list.models.UserEntity;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserAndTasks {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskRepository taskRepository;

    public DefaultUserAndTasks(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder, TaskRepository taskRepository) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskRepository = taskRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addDefaultUserAndTasks() {
        UserEntity userEntity = new UserEntity("user", passwordEncoder.encode("pass"));
        userEntityRepository.save(userEntity);

        Task task1 = new Task("Take out the garbage", "Don't forget", userEntity);
        taskRepository.save(task1);

        Task task2 = new Task("Wash dishes", "Just use dishwasher :D", userEntity);
        taskRepository.save(task2);
    }
}
