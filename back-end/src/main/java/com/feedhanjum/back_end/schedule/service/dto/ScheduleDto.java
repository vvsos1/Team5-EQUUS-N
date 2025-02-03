package com.feedhanjum.back_end.schedule.service.dto;

import com.feedhanjum.back_end.schedule.domain.Schedule;

import java.time.LocalDateTime;

public record ScheduleDto (
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime){
    public ScheduleDto(Schedule schedule){
        this(schedule.getName(), schedule.getStartTime(),schedule.getEndTime());
    }
}
