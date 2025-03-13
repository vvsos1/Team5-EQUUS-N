package com.feedhanjum.feedback.application.port.in.feedback;

import com.feedhanjum.feedback.application.port.in.feedback.command.GetSentFeedbackCountCommand;

public interface GetSentFeedbackCountUseCase {
    Long getSentFeedbackCount(GetSentFeedbackCountCommand command);
}
