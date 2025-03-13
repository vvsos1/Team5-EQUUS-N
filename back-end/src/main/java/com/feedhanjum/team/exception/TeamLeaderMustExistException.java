package com.feedhanjum.team.exception;

public class TeamLeaderMustExistException extends RuntimeException {
    public TeamLeaderMustExistException(String message) {
        super(message);
    }
}
