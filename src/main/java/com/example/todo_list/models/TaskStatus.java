package com.example.todo_list.models;

import org.springframework.util.StringUtils;

public enum TaskStatus {
    UNCOMPLETED,
    COMPLETED;


    @Override
    public String toString() {
        return StringUtils.capitalize(super.toString().toLowerCase());
    }
}
