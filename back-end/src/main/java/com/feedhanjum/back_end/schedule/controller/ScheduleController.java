package com.feedhanjum.back_end.schedule.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.schedule.controller.dto.ScheduleBetweenDurationRequest;
import com.feedhanjum.back_end.schedule.controller.dto.ScheduleRequest;
import com.feedhanjum.back_end.schedule.service.ScheduleService;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleNestedDto;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/team/{teamId}/schedule/create")
    public ResponseEntity<Void> createSchedule(@Login Long memberId, @PathVariable Long teamId, @Valid @RequestBody ScheduleRequest request) {
        scheduleService.createSchedule(memberId, teamId, new ScheduleRequestDto(request));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/team/{teamId}/schedule/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@Login Long memberId, 
                                               @PathVariable Long teamId, 
                                               @PathVariable Long scheduleId, 
                                               @Valid @RequestBody ScheduleRequest request) {
        scheduleService.updateSchedule(memberId, teamId, scheduleId, new ScheduleRequestDto(request));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleNestedDto>> getScheduleBetweenDurations(
            @Login Long memberId,
            @Valid @RequestBody ScheduleBetweenDurationRequest scheduleBetweenDurationRequest
    ){
        List<ScheduleNestedDto> scheduleDurations = scheduleService.getScheduleDurations(
                memberId,
                scheduleBetweenDurationRequest.teamId(),
                scheduleBetweenDurationRequest.startDay(),
                scheduleBetweenDurationRequest.endDay()
        );
        return new ResponseEntity<>(scheduleDurations, HttpStatus.OK);
    }

    @GetMapping("/team/{teamId}/schedule")
    public ResponseEntity<ScheduleNestedDto> getNearestSchedule(
            @Login Long memberId,
            @PathVariable Long teamId
    ){
        ScheduleNestedDto nearestSchedule = scheduleService.getNearestSchedule(memberId, teamId);
        return new ResponseEntity<>(nearestSchedule, HttpStatus.OK);
    }
}
