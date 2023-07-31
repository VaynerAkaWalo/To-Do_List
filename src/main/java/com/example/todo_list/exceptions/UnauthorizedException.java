package com.example.todo_list.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "It's not your task")
public class UnauthorizedException extends RuntimeException{

}
