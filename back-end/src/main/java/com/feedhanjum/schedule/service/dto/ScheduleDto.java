package com.feedhanjum.schedule.service.dto;

import com.feedhanjum.schedule.domain.Schedule;

import java.time.LocalDateTime;

public record ScheduleDto(
        Long id,
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime) {
    public ScheduleDto(Schedule schedule) {
        this(schedule.getId(), schedule.getName(), schedule.getStartTime(), schedule.getEndTime());
    }
}
