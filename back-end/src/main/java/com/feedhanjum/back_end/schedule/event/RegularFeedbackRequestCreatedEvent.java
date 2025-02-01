package com.feedhanjum.back_end.schedule.event;

import lombok.Getter;

@Getter
public class RegularFeedbackRequestCreatedEvent {
    private final Long receiverId;
    private final Long scheduleId;

    public RegularFeedbackRequestCreatedEvent(Long receiverId, Long scheduleId) {
        this.receiverId = receiverId;
        this.scheduleId = scheduleId;
    }
}
