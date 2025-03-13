package com.feedhanjum.auth.exception;

public class LoginStateRequiredException extends RuntimeException {
    public LoginStateRequiredException(String message) {
        super(message);
    }
}
