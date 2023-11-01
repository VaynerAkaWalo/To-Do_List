package com.example.todo_list.models.dto.request;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.TaskStatus;

public record TaskCreationDTO(String name, String details, boolean status) {

    public void to(Task task) {
        task.setName(name);
        task.setDetails(details);

        if (status) {
            task.setAsCompleted();
        }
        else {
            task.setAsUncompleted();
        }
    }

    public static TaskCreationDTO from(Task task) {
        return new TaskCreationDTO(task.getName(), task.getDetails(), task.getStatus() == TaskStatus.COMPLETED);
    }
}
