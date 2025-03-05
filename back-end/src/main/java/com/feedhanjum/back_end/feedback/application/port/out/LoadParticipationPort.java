package com.feedhanjum.back_end.feedback.application.port.out;

import java.util.List;

public interface LoadParticipationPort {

    List<Long> loadParticipation(Long scheduleId);
}
