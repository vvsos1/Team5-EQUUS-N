package com.feedhanjum.back_end.feedback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParticipationNotFound extends RuntimeException {
    public ParticipationNotFound(Long scheduleId, Long memberId) {
        super("Schedule " + scheduleId + " does not have member " + memberId);
    }
}
