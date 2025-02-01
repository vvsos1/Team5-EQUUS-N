package com.feedhanjum.back_end.feedback.event;

import lombok.Getter;

@Getter
public class RegularFeedbackCreatedEvent {
    private final Long feedbackId;

    public RegularFeedbackCreatedEvent(Long feedbackId) {
        this.feedbackId = feedbackId;
    }
}
