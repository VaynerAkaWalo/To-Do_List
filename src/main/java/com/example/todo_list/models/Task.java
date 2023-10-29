package com.example.todo_list.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String details;
    private TaskStatus status;

    private LocalDate createdAt;

    @JsonIgnore
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


    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDate() {
        return createdAt;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAsCompleted() { status = TaskStatus.COMPLETED; }

    public void setAsUncompleted() { status = TaskStatus.UNCOMPLETED; }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
