package com.example.todo_list.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String details;
    private TaskStatus status;

    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntity;

    public Task(String name, String details, UserEntity userEntity) {
        this.name = name;
        this.details = details;
        this.status = TaskStatus.UNCOMPLETED;
        this.userEntity = userEntity;
        this.createdAt = LocalDate.now();
    }

    public Task() {
        this.createdAt = LocalDate.now();
        this.status = TaskStatus.UNCOMPLETED;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDate() {
        return createdAt;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setAsCompleted() { status = TaskStatus.COMPLETED; }

    public void setAsUncompleted() { status = TaskStatus.UNCOMPLETED; }
}
