package com.feedhanjum.back_end.feedback.adapter.in.event;

import com.feedhanjum.back_end.feedback.application.port.in.CreateFeedbackReportUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.CreateFeedbackReportCommand;
import com.feedhanjum.back_end.member.event.MemberRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
class CreateFeedbackReportHandler {
    private final CreateFeedbackReportUseCase createFeedbackReportUseCase;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    void on(MemberRegisteredEvent event) {
        var command = new CreateFeedbackReportCommand(event.memberId());
        createFeedbackReportUseCase.createFeedbackReport(command);
    }
}
