package com.feedhanjum.back_end.feedback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MembershipNotFound extends RuntimeException {
    public MembershipNotFound(Long teamId, Long memberId) {
        super("Team " + teamId + " does not have member " + memberId);
    }
}
