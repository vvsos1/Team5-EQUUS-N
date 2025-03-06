package com.feedhanjum.back_end.feedback.adapter.in.event;

import com.feedhanjum.back_end.feedback.application.port.in.RemoveFeedbackRequestUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.RemoveFeedbackRequestCommand;
import com.feedhanjum.back_end.team.event.TeamMemberLeftEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
class RemoveFeedbackRequestHandler {

    private final RemoveFeedbackRequestUseCase removeFeedbackRequestUseCase;

    @Async
    @Retryable
    @TransactionalEventListener
    void on(TeamMemberLeftEvent event) {
        var command = new RemoveFeedbackRequestCommand(event.teamId(), event.memberId());
        removeFeedbackRequestUseCase.removeFeedbackRequest(command);
    }
}
