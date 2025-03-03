package com.feedhanjum.back_end.feedback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 정기 피드백 요청이 없었는데 정기 피드백을 보낼 경우 발생
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegularFeedbackRequestNotFoundException extends RuntimeException {
    public RegularFeedbackRequestNotFoundException(Long scheduleId, Long receiverId) {
        super("Regular feedback request not found for schedule " + scheduleId + " and receiver " + receiverId);
    }
}
