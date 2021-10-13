package com.example.Demo3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {

    public  AlreadyExistsException(HttpStatus conflict, String message){
        super(message);
    }
}
