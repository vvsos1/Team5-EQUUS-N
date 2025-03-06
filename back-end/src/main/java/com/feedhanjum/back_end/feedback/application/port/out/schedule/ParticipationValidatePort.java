package com.feedhanjum.back_end.feedback.application.port.out.schedule;

public interface ParticipationValidatePort {

    boolean hasParticipation(Long scheduleId, Long memberId);
}
