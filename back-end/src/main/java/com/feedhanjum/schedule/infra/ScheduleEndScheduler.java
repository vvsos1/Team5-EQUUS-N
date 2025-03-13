package com.feedhanjum.schedule.infra;

import com.feedhanjum.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ScheduleEndScheduler {
    private final ScheduleService scheduleService;

    @Scheduled(cron = "0 */10 * * * *")
    public void endSchedule() {
        scheduleService.endSchedules();
    }
}
