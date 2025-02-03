package com.feedhanjum.back_end.schedule.controller;

import com.feedhanjum.back_end.schedule.controller.dto.ScheduleRequest;
import com.feedhanjum.back_end.schedule.domain.Todo;
import com.feedhanjum.back_end.schedule.service.ScheduleService;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleRequestDto;
import com.feedhanjum.back_end.schedule.service.dto.ScheduleResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ScheduleControllerTest {

    @InjectMocks
    private ScheduleController scheduleController;

    @Mock
    private ScheduleService scheduleService;
    @Test
    @DisplayName("일정 생성 컨트롤러 성공 테스트")
    void scheduleController_createSchedule_성공() {
        // given
        Long memberId = 1L;
        Long teamId = 2L;
        ScheduleRequest request = new ScheduleRequest("haha", LocalDateTime.now(), LocalDateTime.now().plusDays(10), List.of(new Todo("hehe")));
        ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto(request);
        ScheduleResponseDto responseDto = mock(ScheduleResponseDto.class);
        when(scheduleService.createSchedule(memberId, teamId, scheduleRequestDto)).thenReturn(responseDto);

        // when
        ResponseEntity<ScheduleResponseDto> result = scheduleController.createSchedule(memberId, teamId, request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(responseDto);
    }
}