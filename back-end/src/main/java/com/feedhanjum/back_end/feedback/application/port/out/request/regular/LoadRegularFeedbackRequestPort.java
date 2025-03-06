package com.feedhanjum.back_end.feedback.application.port.out.request.regular;

import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;

import java.util.List;
import java.util.Optional;

public interface LoadRegularFeedbackRequestPort {

    Optional<RegularFeedbackRequest> load(Long requesterId, Long scheduleId, Long receiverId);

    List<RegularFeedbackRequest> load(Long scheduleId, Long receiverId);

    Long countByScheduleIdAndReceiverId(Long scheduleId, Long receiverId);
}
