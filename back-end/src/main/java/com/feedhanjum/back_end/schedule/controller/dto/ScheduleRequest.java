package com.feedhanjum.back_end.schedule.controller.dto;

import com.feedhanjum.back_end.schedule.domain.Todo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleRequest(
        @NotBlank @Size(min = 1, max = 20) String name,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime,
        List<Todo> todos
) {
}
