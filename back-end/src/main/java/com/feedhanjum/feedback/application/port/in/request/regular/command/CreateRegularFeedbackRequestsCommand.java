package com.feedhanjum.feedback.application.port.in.request.regular.command;

import com.feedhanjum.core.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateRegularFeedbackRequestsCommand extends SelfValidating<CreateRegularFeedbackRequestsCommand> {
    @NotNull
    private final Long scheduleId;

    public CreateRegularFeedbackRequestsCommand(Long scheduleId) {
        this.scheduleId = scheduleId;
        validateSelf();
    }
}
