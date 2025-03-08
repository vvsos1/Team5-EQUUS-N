package com.feedhanjum.back_end.feedback.adapter.in.event;

import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.RemoveRelatedFrequentFeedbackRequestUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.command.RemoveRelatedFrequentFeedbackRequestCommand;
import com.feedhanjum.back_end.feedback.event.FrequentFeedbackCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class RemoveRelatedFrequentFeedbackRequestHandler {
    private final RemoveRelatedFrequentFeedbackRequestUseCase removeRelatedFrequentFeedbackRequestUseCase;

    @Async
    @TransactionalEventListener
    void on(FrequentFeedbackCreatedEvent event) {
        var command = new RemoveRelatedFrequentFeedbackRequestCommand(event.senderId(), event.teamId(), event.receiverId());
        removeRelatedFrequentFeedbackRequestUseCase.deleteRelatedFrequentFeedback(command);
    }
}
