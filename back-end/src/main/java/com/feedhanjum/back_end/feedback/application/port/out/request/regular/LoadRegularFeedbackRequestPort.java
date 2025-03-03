package com.feedhanjum.back_end.feedback.application.port.out.request.regular;

import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;

import java.util.Optional;

public interface LoadRegularFeedbackRequestPort {

    Optional<RegularFeedbackRequest> loadRegularFeedbackRequest(Long requesterId, Long scheduleId, Long receiverId);
}
