package com.feedhanjum.back_end.schedule.event.handler;

import com.feedhanjum.back_end.schedule.service.ScheduleService;
import com.feedhanjum.back_end.team.event.TeamMemberJoinEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AddScheduleMemberEventHandler {

    private final ScheduleService scheduleService;

    @Async
    @EventListener
    public void addNewScheduleMember(TeamMemberJoinEvent event) {
        try {
            scheduleService.addNewScheduleMembership(event.memberId(), event.teamId());
        } catch (Exception e) {
            // Log the error and potentially trigger a compensating action
            log.error("Failed to add schedule membership for member {} in team {}: {}",
                      event.memberId(), event.teamId(), e.getMessage(), e);
        }
    }
}
