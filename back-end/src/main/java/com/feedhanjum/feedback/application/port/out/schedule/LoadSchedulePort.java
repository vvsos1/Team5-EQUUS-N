package com.feedhanjum.feedback.application.port.out.schedule;

import com.feedhanjum.feedback.domain.AssociatedSchedule;

import java.util.Optional;

public interface LoadSchedulePort {
    Optional<AssociatedSchedule> loadSchedule(Long scheduleId);
}
