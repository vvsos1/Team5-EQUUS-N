package com.feedhanjum.back_end.feedback.application.port.in.request.regular;

import com.feedhanjum.back_end.feedback.application.port.in.request.regular.command.SkipRegularFeedbackRequestCommand;

public interface SkipRegularFeedbackRequestUseCase {
    void skipRegularFeedbackRequest(SkipRegularFeedbackRequestCommand command);
}
