package com.example.todo_list.models;

public record TaskDTO(String name, String details) {

    public Task TaskDTOtoTask(UserEntity userEntity) {
        Task task = new Task();

        task.setName(this.name);
        task.setDetails(this.details);
        task.setUserEntity(userEntity);

        return task;
    }
}
