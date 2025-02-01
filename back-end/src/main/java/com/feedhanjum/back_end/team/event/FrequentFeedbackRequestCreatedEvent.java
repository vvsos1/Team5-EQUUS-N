package com.feedhanjum.back_end.team.event;

import lombok.Getter;

@Getter
public class FrequentFeedbackRequestCreatedEvent {
    private final Long frequentFeedbackRequestId;

    public FrequentFeedbackRequestCreatedEvent(Long frequentFeedbackRequestId) {
        this.frequentFeedbackRequestId = frequentFeedbackRequestId;
    }
}
