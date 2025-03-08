package com.feedhanjum.back_end.feedback.application.port.in.feedback;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.GetReceivedFeedbackCountCommand;

public interface GetReceivedFeedbackCountUseCase {
    Long getReceivedFeedbackCount(GetReceivedFeedbackCountCommand command);
}
