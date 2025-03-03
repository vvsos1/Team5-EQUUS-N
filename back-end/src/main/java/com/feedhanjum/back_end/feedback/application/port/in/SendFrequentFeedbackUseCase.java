package com.feedhanjum.back_end.feedback.application.port.in;

import com.feedhanjum.back_end.feedback.application.port.in.command.SendFrequentFeedbackCommand;

public interface SendFrequentFeedbackUseCase {

    void sendFrequentFeedback(SendFrequentFeedbackCommand command);
}
