package com.feedhanjum.feedback.application.port.out.request.regular;

import com.feedhanjum.feedback.domain.RegularFeedbackRequest;

public interface SaveRegularFeedbackRequestPort {

    void save(RegularFeedbackRequest request);
}
