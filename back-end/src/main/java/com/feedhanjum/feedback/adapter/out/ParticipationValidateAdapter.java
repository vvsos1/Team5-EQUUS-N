package com.feedhanjum.feedback.adapter.out;

import com.feedhanjum.feedback.application.port.out.schedule.ParticipationValidatePort;
import com.feedhanjum.schedule.repository.ScheduleMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class ParticipationValidateAdapter implements ParticipationValidatePort {
    private final ScheduleMemberRepository scheduleMemberRepository;

    @Override
    public boolean hasParticipation(Long scheduleId, Long memberId) {
        return scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, scheduleId).isPresent();
    }
}
