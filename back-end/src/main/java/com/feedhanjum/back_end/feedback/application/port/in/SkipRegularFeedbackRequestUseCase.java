package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.SkipRegularFeedbackRequestCommand;

public interface SkipRegularFeedbackRequestUseCase {
    void skipRegularFeedbackRequest(SkipRegularFeedbackRequestCommand command);
}
