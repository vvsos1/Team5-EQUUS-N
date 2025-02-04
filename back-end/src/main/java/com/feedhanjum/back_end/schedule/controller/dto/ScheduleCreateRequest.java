package com.feedhanjum.back_end.schedule.controller.dto;

import com.feedhanjum.back_end.schedule.domain.Todo;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleCreateRequest(
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime,
        List<Todo> todos
) {
}
