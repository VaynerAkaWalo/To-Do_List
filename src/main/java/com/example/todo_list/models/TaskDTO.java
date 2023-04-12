package com.example.todo_list.models;

public record TaskDTO(String name, String details) {

    public Task TaskDTOtoTask(Task task) {
        task.setName(this.name);
        task.setDetails(this.details);
        return task;
    }
}
