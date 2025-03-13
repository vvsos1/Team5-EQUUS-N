package com.feedhanjum.feedback.adapter.out;

import com.feedhanjum.feedback.application.port.out.schedule.LoadSchedulePort;
import com.feedhanjum.feedback.domain.AssociatedSchedule;
import com.feedhanjum.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
class LoadScheduleAdapter implements LoadSchedulePort {
    private final ScheduleRepository scheduleRepository;

    @Override
    public Optional<AssociatedSchedule> loadSchedule(Long scheduleId) {
        return scheduleRepository
                .findById(scheduleId)
                .map(AssociatedSchedule::of);
    }
}
