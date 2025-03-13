package com.feedhanjum.feedback.application.port.out.schedule;

public interface ParticipationValidatePort {

    boolean hasParticipation(Long scheduleId, Long memberId);
}
