package com.feedhanjum.back_end.feedback.application.port.out.schedule;

import com.feedhanjum.back_end.feedback.domain.AssociatedSchedule;

import java.util.Optional;

public interface LoadSchedulePort {
    Optional<AssociatedSchedule> loadSchedule(Long scheduleId);
}
