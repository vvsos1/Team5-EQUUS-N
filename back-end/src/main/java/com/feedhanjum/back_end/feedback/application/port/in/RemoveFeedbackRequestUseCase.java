package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.RemoveFeedbackRequestCommand;

public interface RemoveFeedbackRequestUseCase {
    void removeFeedbackRequest(RemoveFeedbackRequestCommand command);
}
