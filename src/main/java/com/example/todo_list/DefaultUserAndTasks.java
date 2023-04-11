package com.example.todo_list;

import com.example.todo_list.data.TaskRepository;
import com.example.todo_list.data.UserEntityRepository;
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

        Task task = new Task("Wynieść śmieci", false, userEntity);
        taskRepository.save(task);
    }
}
