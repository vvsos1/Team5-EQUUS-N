package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.GetReceivedFeedbackCountCommand;

public interface GetReceivedFeedbackCountUseCase {
    Long getReceivedFeedbackCount(GetReceivedFeedbackCountCommand command);
}
