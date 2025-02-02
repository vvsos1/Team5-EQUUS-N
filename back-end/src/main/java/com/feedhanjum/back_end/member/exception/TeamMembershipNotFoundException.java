package com.feedhanjum.back_end.member.exception;

public class TeamMembershipNotFoundException extends RuntimeException {
    public TeamMembershipNotFoundException(String message) {
        super(message);
    }
}
