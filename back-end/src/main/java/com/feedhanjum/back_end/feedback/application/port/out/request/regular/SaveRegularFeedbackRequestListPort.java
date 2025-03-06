package com.feedhanjum.back_end.feedback.application.port.out.request.regular;

import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;

import java.util.List;

public interface SaveRegularFeedbackRequestListPort {
    void saveAll(List<RegularFeedbackRequest> requests);
}
