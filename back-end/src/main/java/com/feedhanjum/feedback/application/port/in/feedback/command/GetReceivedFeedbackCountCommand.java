package com.feedhanjum.feedback.application.port.in.feedback.command;

import com.feedhanjum.core.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GetReceivedFeedbackCountCommand extends SelfValidating<GetReceivedFeedbackCountCommand> {
    @NotNull
    private final Long receiverId;

    public GetReceivedFeedbackCountCommand(Long receiverId) {
        this.receiverId = receiverId;
        validateSelf();
    }

}
