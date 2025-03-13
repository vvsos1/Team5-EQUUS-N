package com.feedhanjum.feedback.application.port.in.feedback;

import com.feedhanjum.feedback.application.port.in.feedback.command.GetReceivedFeedbackCountCommand;

public interface GetReceivedFeedbackCountUseCase {
    Long getReceivedFeedbackCount(GetReceivedFeedbackCountCommand command);
}
