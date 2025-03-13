package com.feedhanjum.feedback.application.port.out.schedule;

import java.util.List;

public interface LoadParticipationPort {

    List<Long> loadParticipation(Long scheduleId);
}
