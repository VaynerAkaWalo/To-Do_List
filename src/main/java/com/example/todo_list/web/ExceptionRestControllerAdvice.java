package com.example.todo_list.web;

import com.example.todo_list.exceptions.ForbiddenException;
import com.example.todo_list.exceptions.TaskNotFoundException;
import com.example.todo_list.exceptions.UnauthorizedException;
import com.example.todo_list.exceptions.UserNotFoundException;
import com.example.todo_list.models.ErrorMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {

    @Value("${error.usernotfound}")
    String userNotFoundMessage;

    @Value("${error.unauthorized}")
    String unauthorizedMessage;

    @Value("${error.forbidden}")
    String forbiddenMessage;

    @Value("${error.tasknotfound}")
    String taskNotFoundMessage;

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage userNotFoundException() {
        return new ErrorMessage(userNotFoundMessage);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage unauthorizedException() {
        return new ErrorMessage(unauthorizedMessage);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage forbiddenException() {
        return new ErrorMessage(forbiddenMessage);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage taskNotFoundException() {
        return new ErrorMessage(taskNotFoundMessage);
    }
}
