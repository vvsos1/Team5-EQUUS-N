package com.feedhanjum.feedback.application.port.in.request.frequent;

import com.feedhanjum.feedback.application.port.in.request.frequent.command.RemoveRelatedFrequentFeedbackRequestCommand;

public interface RemoveRelatedFrequentFeedbackRequestUseCase {
    void deleteRelatedFrequentFeedback(RemoveRelatedFrequentFeedbackRequestCommand command);
}
