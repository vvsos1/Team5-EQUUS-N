package com.feedhanjum.feedback.application.port.out.request.frequent;

import java.util.Collection;
import java.util.List;

public interface DeleteFrequentFeedbackRequestPort {

    default void deleteById(Long frequentFeedbackRequestId) {
        deleteByIds(List.of(frequentFeedbackRequestId));
    }

    void deleteByIds(Collection<Long> frequentFeedbackRequestIds);

    void deleteByTeamIdAndReceiverId(Long teamId, Long receiverId);

    void deleteByTeamIdAndRequesterId(Long teamId, Long requesterId);
}
