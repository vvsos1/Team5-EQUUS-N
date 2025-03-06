package com.feedhanjum.back_end.feedback.application.port.out.request.regular;

import java.util.Collection;
import java.util.List;

public interface DeleteRegularFeedbackRequestPort {

    default void deleteRegularFeedbackRequest(Long regularFeedbackRequestId) {
        deleteRegularFeedbackRequests(List.of(regularFeedbackRequestId));
    }

    void deleteRegularFeedbackRequests(Collection<Long> regularFeedbackRequestIds);
}
