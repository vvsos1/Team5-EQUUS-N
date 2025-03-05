package com.feedhanjum.back_end.feedback.exception;

import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FeedbackNotFound extends RuntimeException {
    public FeedbackNotFound(FeedbackId feedbackId) {
        super("Feedback " + feedbackId + " not found");
    }
}
