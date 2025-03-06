package com.feedhanjum.back_end.feedback.adapter.in.event;

import com.feedhanjum.back_end.feedback.application.port.in.ApplyFeedbackReportUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.ApplyFeedbackReportCommand;
import com.feedhanjum.back_end.feedback.event.FrequentFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.event.RegularFeedbackCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class ApplyFeedbackReportHandler {
    private final ApplyFeedbackReportUseCase applyFeedbackReportUseCase;

    @Async
    @TransactionalEventListener
    void on(RegularFeedbackCreatedEvent event) {
        var command = new ApplyFeedbackReportCommand(event.receiverId(), event.feedbackId());
        applyFeedbackReportUseCase.applyFeedbackReport(command);
    }

    @Async
    @TransactionalEventListener
    void on(FrequentFeedbackCreatedEvent event) {
        var command = new ApplyFeedbackReportCommand(event.receiverId(), event.feedbackId());
        applyFeedbackReportUseCase.applyFeedbackReport(command);
    }
}
