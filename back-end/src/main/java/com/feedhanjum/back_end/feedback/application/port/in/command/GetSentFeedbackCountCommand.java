package com.feedhanjum.back_end.feedback.application.port.in.command;

import com.feedhanjum.back_end.core.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GetSentFeedbackCountCommand extends SelfValidating<GetSentFeedbackCountCommand> {
    @NotNull
    private final Long senderId;

    public GetSentFeedbackCountCommand(Long senderId) {
        this.senderId = senderId;
        validateSelf();
    }
}
