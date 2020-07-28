package com.github.krzysiek199720.codeclass.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {

    protected <T> ResponseEntity<T> okResponse(T object){
        if(object == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    protected <T> ResponseEntity<T> createdResponse(T object){
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }

    protected <T> ResponseEntity<T> makeResponse(HttpStatus status, T object){
        return ResponseEntity.status(status).body(object);
    }

    protected ResponseEntity<Object> noContent(){
        return ResponseEntity.noContent().build();
    }

}
