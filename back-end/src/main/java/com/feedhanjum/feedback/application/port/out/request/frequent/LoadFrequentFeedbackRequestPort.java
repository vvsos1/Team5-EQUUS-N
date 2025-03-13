package com.feedhanjum.feedback.application.port.out.request.frequent;

import com.feedhanjum.feedback.domain.FrequentFeedbackRequest;

import java.util.List;
import java.util.Optional;

public interface LoadFrequentFeedbackRequestPort {
    Optional<FrequentFeedbackRequest> load(Long requesterId, Long teamId, Long receiverId);

    List<FrequentFeedbackRequest> load(Long teamId, Long receiverId);
}
