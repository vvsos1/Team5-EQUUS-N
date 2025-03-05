package com.feedhanjum.back_end.feedback.application.port.in.command;

import com.feedhanjum.back_end.core.SelfValidating;
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
