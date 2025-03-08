package com.feedhanjum.back_end.feedback.adapter.in.event;

import com.feedhanjum.back_end.feedback.application.port.in.request.RemoveFeedbackRequestUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.command.RemoveFeedbackRequestCommand;
import com.feedhanjum.back_end.team.event.TeamMemberLeftEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
class RemoveFeedbackRequestCommandHandler {
    private final RemoveFeedbackRequestUseCase removeFeedbackRequestUseCase;

    @Async
    @TransactionalEventListener
    void on(TeamMemberLeftEvent event) {
        var command = new RemoveFeedbackRequestCommand(event.teamId(), event.memberId());
        removeFeedbackRequestUseCase.removeFeedbackRequest(command);
    }

}
