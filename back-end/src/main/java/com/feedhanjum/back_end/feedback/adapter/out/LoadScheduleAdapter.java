package com.feedhanjum.back_end.feedback.adapter.out;

import com.feedhanjum.back_end.feedback.application.port.out.schedule.LoadSchedulePort;
import com.feedhanjum.back_end.feedback.domain.AssociatedSchedule;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoadScheduleAdapter implements LoadSchedulePort {
    private final ScheduleRepository scheduleRepository;

    @Override
    public Optional<AssociatedSchedule> loadSchedule(Long scheduleId) {
        return scheduleRepository
                .findById(scheduleId)
                .map(AssociatedSchedule::of);
    }
}
