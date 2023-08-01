package com.example.todo_list.models;

public record TaskDTO(String name, String details, boolean status) {

    public void transferDataToTask(Task task) {
        task.setName(name);
        task.setDetails(details);

        if (status) {
            task.setAsCompleted();
        }
        else {
            task.setAsUncompleted();
        }
    }

    public static TaskDTO TaskToDTO(Task task) {
        return new TaskDTO(task.getName(), task.getDetails(), task.getStatus() == TaskStatus.COMPLETED);
    }
}
