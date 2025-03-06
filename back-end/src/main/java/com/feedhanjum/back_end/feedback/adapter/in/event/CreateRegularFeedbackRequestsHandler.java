package com.feedhanjum.back_end.feedback.adapter.in.event;

import com.feedhanjum.back_end.feedback.application.port.in.CreateRegularFeedbackRequestsUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.CreateRegularFeedbackRequestsCommand;
import com.feedhanjum.back_end.schedule.event.ScheduleEndedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
class CreateRegularFeedbackRequestsHandler {
    private final CreateRegularFeedbackRequestsUseCase createRegularFeedbackRequestsUseCase;

    @Async
    @Retryable
    @TransactionalEventListener
    void on(ScheduleEndedEvent event) {
        var command = new CreateRegularFeedbackRequestsCommand(event.scheduleId());
        createRegularFeedbackRequestsUseCase.createRegularFeedbackRequests(command);
    }
}
