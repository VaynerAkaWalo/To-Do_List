package com.example.todo_list.models;

public record TaskDTO(String name, String details, boolean status) {

    public Task TaskDTOtoTask(UserEntity userEntity) {
        Task task = new Task();

        task.setName(this.name);
        task.setDetails(this.details);
        if (status) {
            task.setAsCompleted();
        }
        task.setUserEntity(userEntity);

        return task;
    }
}
