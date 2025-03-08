package com.feedhanjum.back_end.feedback.application.port.in.request.regular;

import com.feedhanjum.back_end.feedback.application.port.in.request.regular.command.CreateRegularFeedbackRequestsCommand;

public interface CreateRegularFeedbackRequestsUseCase {

    void createRegularFeedbackRequests(CreateRegularFeedbackRequestsCommand command);
}
