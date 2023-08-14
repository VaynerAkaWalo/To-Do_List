package com.example.todo_list.web;

import com.example.todo_list.exceptions.TaskNotFoundException;
import com.example.todo_list.exceptions.UnauthorizedException;
import com.example.todo_list.exceptions.UserNotFoundException;
import com.example.todo_list.models.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException() {
        ErrorMessage errorMessage = new ErrorMessage("User not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessage> unauthorizedException() {
        ErrorMessage errorMessage = new ErrorMessage("It's not your task!");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorMessage> taskNotFoundException() {
        ErrorMessage errorMessage = new ErrorMessage("Task not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
