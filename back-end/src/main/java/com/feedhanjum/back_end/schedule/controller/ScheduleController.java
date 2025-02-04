package com.feedhanjum.back_end.schedule.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.schedule.controller.dto.ScheduleRequest;
import com.feedhanjum.back_end.schedule.service.ScheduleService;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleRequestDto;
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
    public ResponseEntity<Void> createSchedule(@Login Long memberId, @PathVariable Long teamId, @Valid @RequestBody ScheduleRequest request) {
        scheduleService.createSchedule(memberId, teamId, new ScheduleRequestDto(request));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
