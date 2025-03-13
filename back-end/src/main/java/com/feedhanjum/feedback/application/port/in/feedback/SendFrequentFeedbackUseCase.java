package com.feedhanjum.feedback.application.port.in.feedback;

import com.feedhanjum.feedback.application.port.in.feedback.command.SendFrequentFeedbackCommand;

public interface SendFrequentFeedbackUseCase {

    void sendFrequentFeedback(SendFrequentFeedbackCommand command);
}
