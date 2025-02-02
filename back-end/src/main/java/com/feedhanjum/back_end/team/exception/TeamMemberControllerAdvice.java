package com.feedhanjum.back_end.team.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TeamMemberControllerAdvice {
    @ExceptionHandler(TeamMembershipNotFoundException.class)
    public ResponseEntity<String> teamMembershipNotFoundException(TeamMembershipNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
