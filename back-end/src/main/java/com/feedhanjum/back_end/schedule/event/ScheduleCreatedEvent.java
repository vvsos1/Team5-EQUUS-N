package com.feedhanjum.back_end.schedule.event;

import lombok.Getter;

@Getter
public class ScheduleCreatedEvent {
    private final Long scheduleId;

    public ScheduleCreatedEvent(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
