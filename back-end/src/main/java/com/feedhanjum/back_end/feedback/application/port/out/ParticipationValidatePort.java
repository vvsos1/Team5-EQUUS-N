package com.feedhanjum.back_end.feedback.application.port.out;

public interface ParticipationValidatePort {

    boolean hasParticipation(Long scheduleId, Long memberId);
}
