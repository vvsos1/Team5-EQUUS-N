package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.CreateRegularFeedbackRequestsCommand;

public interface CreateRegularFeedbackRequestsUseCase {

    void createRegularFeedbackRequests(CreateRegularFeedbackRequestsCommand command);
}
