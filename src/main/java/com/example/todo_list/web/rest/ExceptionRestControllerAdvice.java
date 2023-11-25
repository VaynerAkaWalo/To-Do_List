package com.example.todo_list.web.rest;

import com.example.todo_list.exceptions.ForbiddenException;
import com.example.todo_list.exceptions.TaskNotFoundException;
import com.example.todo_list.exceptions.UnauthorizedException;
import com.example.todo_list.exceptions.UserNotFoundException;
import com.example.todo_list.models.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {

    private final Environment env;

    @Autowired
    public ExceptionRestControllerAdvice(Environment env) {
        this.env = env;
    }

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail userNotFoundException() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, env.getProperty("error.usernotfound", ""));
    }

    @ExceptionHandler(UnauthorizedException.class)
    ProblemDetail unauthorizedException() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, env.getProperty("error.unauthorized", ""));
    }

    @ExceptionHandler(ForbiddenException.class)
    ProblemDetail forbiddenException() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, env.getProperty("error.forbidden", ""));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ProblemDetail taskNotFoundException() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, env.getProperty("error.tasknotfound", ""));
    }
}
