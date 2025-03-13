package com.feedhanjum.schedule.exception;

public class ScheduleIsAlreadyEndException extends RuntimeException {
    public ScheduleIsAlreadyEndException(String message) {
        super(message);
    }
}
