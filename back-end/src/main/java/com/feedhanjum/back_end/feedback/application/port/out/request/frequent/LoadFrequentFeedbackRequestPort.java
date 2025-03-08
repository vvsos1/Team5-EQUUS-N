package com.feedhanjum.back_end.feedback.application.port.out.request.frequent;

import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;

import java.util.List;
import java.util.Optional;

public interface LoadFrequentFeedbackRequestPort {
    Optional<FrequentFeedbackRequest> load(Long requesterId, Long teamId, Long receiverId);

    List<FrequentFeedbackRequest> load(Long teamId, Long receiverId);
}
