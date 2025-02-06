package com.feedhanjum.back_end.schedule.event;

import lombok.Getter;

@Getter
public class ScheduleEndedEvent {
    private final Long scheduleId;

    public ScheduleEndedEvent(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
