package com.feedhanjum.schedule.exception;

public class ScheduleAlreadyExistException extends RuntimeException {
    public ScheduleAlreadyExistException(String message) {
        super(message);
    }
}
