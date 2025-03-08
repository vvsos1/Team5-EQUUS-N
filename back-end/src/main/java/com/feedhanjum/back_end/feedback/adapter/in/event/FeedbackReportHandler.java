package com.feedhanjum.back_end.feedback.adapter.in.event;

import com.feedhanjum.back_end.feedback.application.port.in.ApplyFeedbackReportUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.CreateFeedbackReportUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.ApplyFeedbackReportCommand;
import com.feedhanjum.back_end.feedback.application.port.in.command.CreateFeedbackReportCommand;
import com.feedhanjum.back_end.feedback.event.FrequentFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.event.RegularFeedbackCreatedEvent;
import com.feedhanjum.back_end.member.event.MemberRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
class FeedbackReportHandler {
    private final CreateFeedbackReportUseCase createFeedbackReportUseCase;
    private final ApplyFeedbackReportUseCase applyFeedbackReportUseCase;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    void on(MemberRegisteredEvent event) {
        var command = new CreateFeedbackReportCommand(event.memberId());
        createFeedbackReportUseCase.createFeedbackReport(command);
    }

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
