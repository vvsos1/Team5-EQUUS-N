package com.feedhanjum.feedback.adapter.out;

import com.feedhanjum.feedback.application.port.out.schedule.LoadParticipationPort;
import com.feedhanjum.schedule.domain.ScheduleMember;
import com.feedhanjum.schedule.repository.ScheduleMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
class LoadParticipationAdapter implements LoadParticipationPort {
    ScheduleMemberRepository scheduleMemberRepository;

    @Override
    public List<Long> loadParticipation(Long scheduleId) {
        List<ScheduleMember> scheduleMembers = scheduleMemberRepository.findAllByScheduleId(scheduleId);
        return scheduleMembers.stream().map(sm -> sm.getMember().getId()).toList();
    }
}
