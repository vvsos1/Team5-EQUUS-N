package com.feedhanjum.back_end.schedule.service.dto;

import com.feedhanjum.back_end.schedule.controller.dto.ScheduleCreateRequest;
import com.feedhanjum.back_end.schedule.domain.Todo;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleRequestDto (String name, LocalDateTime startTime, LocalDateTime endTime, List<Todo> todos) {
    public ScheduleRequestDto(ScheduleCreateRequest request){
        this(request.name(), request.startTime(), request.endTime(),request.todos());
    }
}
