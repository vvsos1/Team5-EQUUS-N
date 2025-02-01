package com.feedhanjum.back_end.feedback.event;

import lombok.Getter;

@Getter
public class FeedbackLikedEvent {
    private final Long feedbackId;

    public FeedbackLikedEvent(Long feedbackId) {
        this.feedbackId = feedbackId;
    }
}
