package com.feedhanjum.schedule.service.dto;

import com.feedhanjum.schedule.controller.dto.ScheduleRequest;
import com.feedhanjum.schedule.domain.Todo;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleRequestDto(String name, LocalDateTime startTime, LocalDateTime endTime, List<Todo> todos) {
    public ScheduleRequestDto(ScheduleRequest request) {
        this(request.name(), request.startTime(), request.endTime(), request.todos());
    }
}
