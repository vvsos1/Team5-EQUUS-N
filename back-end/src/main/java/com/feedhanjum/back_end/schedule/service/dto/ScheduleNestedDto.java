package com.feedhanjum.back_end.schedule.service.dto;

import com.feedhanjum.back_end.schedule.repository.dto.ScheduleProjectionDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleNestedDto{
    private Long scheduleId;
    private String scheduleName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long teamId;
    private String teamName;
    private List<ScheduleMemberNestedDto> scheduleMemberNestedDtoList = new ArrayList<>();

    public ScheduleNestedDto(ScheduleProjectionDto scheduleProjectionDto) {
        scheduleId = scheduleProjectionDto.getScheduleId();
        scheduleName = scheduleProjectionDto.getScheduleName();
        startTime = scheduleProjectionDto.getStartTime();
        endTime = scheduleProjectionDto.getEndTime();
        teamId = scheduleProjectionDto.getTeamId();
        teamName = scheduleProjectionDto.getTeamName();
    }

    public void addScheduleMemberNestedDto(ScheduleMemberNestedDto scheduleMemberNestedDto){
        scheduleMemberNestedDtoList.add(scheduleMemberNestedDto);
    }
}
