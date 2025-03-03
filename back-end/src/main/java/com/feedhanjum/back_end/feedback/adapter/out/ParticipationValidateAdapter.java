package com.feedhanjum.back_end.feedback.adapter.out;

import com.feedhanjum.back_end.feedback.application.port.out.ParticipationValidatePort;
import com.feedhanjum.back_end.schedule.repository.ScheduleMemberRepository;
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
