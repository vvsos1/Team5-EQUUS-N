package com.feedhanjum.back_end.feedback.application.port.in.feedback;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.SendFrequentFeedbackCommand;

public interface SendFrequentFeedbackUseCase {

    void sendFrequentFeedback(SendFrequentFeedbackCommand command);
}
