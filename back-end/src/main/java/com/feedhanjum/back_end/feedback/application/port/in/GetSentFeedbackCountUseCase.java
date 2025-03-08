package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.GetSentFeedbackCountCommand;

public interface GetSentFeedbackCountUseCase {
    Long getSentFeedbackCount(GetSentFeedbackCountCommand command);
}
