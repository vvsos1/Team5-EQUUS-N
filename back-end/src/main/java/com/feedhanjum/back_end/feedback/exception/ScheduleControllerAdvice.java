package com.feedhanjum.back_end.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScheduleControllerAdvice {

    @ExceptionHandler(ScheduleAlreadyExistException.class)
    public ResponseEntity<String> ScheduleAlreadyExists(ScheduleAlreadyExistException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
