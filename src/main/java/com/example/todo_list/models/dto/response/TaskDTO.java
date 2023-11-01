package com.example.todo_list.models.dto.response;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.TaskStatus;

public record TaskDTO(Long id, String name, String details, Boolean status) {

    public static TaskDTO from(Task task) {
        return new TaskDTO(task.getId(), task.getName(), task.getDetails(), task.getStatus() == TaskStatus.COMPLETED);
    }
}
