package com.feedhanjum.schedule.service.dto;

import com.feedhanjum.schedule.domain.Schedule;
import com.feedhanjum.schedule.domain.ScheduleMember;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleResponseDto {
    private final ScheduleDto schedule;
    private final List<TodoListDto> todoListDto;

    public ScheduleResponseDto(Schedule schedule, List<ScheduleMember> scheduleMember) {
        this.schedule = new ScheduleDto(schedule);
        this.todoListDto = scheduleMember.stream()
                .map(TodoListDto::new)
                .toList();
    }
}