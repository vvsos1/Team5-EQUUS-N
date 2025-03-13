package com.feedhanjum.feedback.application.port.in.request.regular;

import com.feedhanjum.feedback.application.port.in.request.regular.command.CreateRegularFeedbackRequestsCommand;

public interface CreateRegularFeedbackRequestsUseCase {

    void createRegularFeedbackRequests(CreateRegularFeedbackRequestsCommand command);
}
