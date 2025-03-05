package com.feedhanjum.back_end.feedback.application.port.out.request.regular;

import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;

import java.util.List;

public interface SaveRegularFeedbackRequestPort {

    void saveRegularFeedbackRequest(RegularFeedbackRequest request);

    void saveRegularFeedbackRequests(List<RegularFeedbackRequest> requests);
}
