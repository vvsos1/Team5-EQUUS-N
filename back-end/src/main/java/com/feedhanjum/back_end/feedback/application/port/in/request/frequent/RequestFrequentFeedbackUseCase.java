package com.feedhanjum.back_end.feedback.application.port.in.request.frequent;

import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.command.RequestFrequentFeedbackCommand;

public interface RequestFrequentFeedbackUseCase {

    void requestFrequentFeedback(RequestFrequentFeedbackCommand command);

}
