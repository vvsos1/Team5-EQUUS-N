package com.feedhanjum.back_end.feedback.event;

import lombok.Getter;

@Getter
public class FrequentFeedbackCreatedEvent {
    private final Long feedbackId;

    public FrequentFeedbackCreatedEvent(Long feedbackId) {
        this.feedbackId = feedbackId;
    }
}
