package com.example.todo_list.data;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.UserEntity;

import java.util.List;

public interface TasksService {
    public List<Task> getUserTasks(String username);
}
