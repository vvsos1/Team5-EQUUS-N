package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.RemoveRelatedFrequentFeedbackRequestCommand;

public interface RemoveRelatedFrequentFeedbackRequestUseCase {
    void deleteRelatedFrequentFeedback(RemoveRelatedFrequentFeedbackRequestCommand command);
}
