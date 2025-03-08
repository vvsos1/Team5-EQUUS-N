package com.feedhanjum.back_end.feedback.application.port.in.feedback;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.GetSentFeedbackCountCommand;

public interface GetSentFeedbackCountUseCase {
    Long getSentFeedbackCount(GetSentFeedbackCountCommand command);
}
