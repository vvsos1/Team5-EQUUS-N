package com.feedhanjum.back_end.feedback.application.port.in.request.frequent;

import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.command.RemoveRelatedFrequentFeedbackRequestCommand;

public interface RemoveRelatedFrequentFeedbackRequestUseCase {
    void deleteRelatedFrequentFeedback(RemoveRelatedFrequentFeedbackRequestCommand command);
}
