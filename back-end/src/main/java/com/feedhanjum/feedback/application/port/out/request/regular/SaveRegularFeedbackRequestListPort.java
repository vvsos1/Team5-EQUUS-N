package com.feedhanjum.feedback.application.port.out.request.regular;

import com.feedhanjum.feedback.domain.RegularFeedbackRequest;

import java.util.List;

public interface SaveRegularFeedbackRequestListPort {
    void saveAll(List<RegularFeedbackRequest> requests);
}
