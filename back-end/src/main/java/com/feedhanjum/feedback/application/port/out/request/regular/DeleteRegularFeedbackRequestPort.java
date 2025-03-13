package com.feedhanjum.feedback.application.port.out.request.regular;

import java.util.Collection;
import java.util.List;

public interface DeleteRegularFeedbackRequestPort {

    default void deleteById(Long regularFeedbackRequestId) {
        deleteByIds(List.of(regularFeedbackRequestId));
    }

    void deleteByIds(Collection<Long> regularFeedbackRequestIds);

    void deleteByTeamIdAndReceiverId(Long teamId, Long receiverId);

    void deleteByTeamIdAndRequesterId(Long teamId, Long requesterId);
}
