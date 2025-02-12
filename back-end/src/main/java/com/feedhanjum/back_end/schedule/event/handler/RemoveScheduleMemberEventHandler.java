package com.feedhanjum.back_end.schedule.event.handler;

import com.feedhanjum.back_end.schedule.service.ScheduleService;
import com.feedhanjum.back_end.team.event.TeamMemberLeftEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RemoveScheduleMemberEventHandler {

    private final ScheduleService scheduleService;

    @Async
    @EventListener
    public void removeRemainScheduleMember(TeamMemberLeftEvent event) {
        scheduleService.removeRemainScheduleMembership(event.memberId(), event.teamId());
    }
}
