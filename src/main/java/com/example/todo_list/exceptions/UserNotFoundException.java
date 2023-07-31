package com.example.todo_list.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "User not found")
public class UserNotFoundException extends RuntimeException{

}
