package com.feedhanjum.back_end.schedule.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.schedule.controller.dto.ScheduleRequest;
import com.feedhanjum.back_end.schedule.service.ScheduleService;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleRequestDto;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team/{teamId}/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Login Long memberId, @PathVariable Long teamId, @Valid @RequestBody ScheduleRequest request) {
        ScheduleResponseDto schedule = scheduleService.createSchedule(memberId, teamId, new ScheduleRequestDto(request));
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }
}
