package com.feedhanjum.feedback.application.port.in.request.regular;

import com.feedhanjum.feedback.application.port.in.request.regular.command.SkipRegularFeedbackRequestCommand;

public interface SkipRegularFeedbackRequestUseCase {
    void skipRegularFeedbackRequest(SkipRegularFeedbackRequestCommand command);
}
