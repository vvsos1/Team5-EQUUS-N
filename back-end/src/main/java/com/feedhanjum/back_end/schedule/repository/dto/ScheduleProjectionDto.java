package com.feedhanjum.back_end.schedule.repository.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleProjectionDto {
    private Long teamId;
    private String teamName;
    private Long scheduleId;
    private String scheduleName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long memberId;
    private String memberName;
    private Long scheduleMemberId;
    private String todo;
}
