package com.feedhanjum.back_end.schedule.event;

import lombok.Getter;

@Getter
public class ScheduleEndEvent {
    private final Long scheduleId;

    public ScheduleEndEvent(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
