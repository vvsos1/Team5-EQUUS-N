package com.feedhanjum.feedback.application.port.in.request;

import com.feedhanjum.feedback.application.port.in.request.command.RemoveFeedbackRequestCommand;

public interface RemoveFeedbackRequestUseCase {
    void removeFeedbackRequest(RemoveFeedbackRequestCommand command);
}
