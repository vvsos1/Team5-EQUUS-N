package com.feedhanjum.feedback.application.port.in.request.regular.command;

import com.feedhanjum.core.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SkipRegularFeedbackRequestCommand extends SelfValidating<SkipRegularFeedbackRequestCommand> {
    @NotNull
    private final Long scheduleId;
    @NotNull
    private final Long receiverId;

    public SkipRegularFeedbackRequestCommand(Long scheduleId, Long receiverId) {
        this.scheduleId = scheduleId;
        this.receiverId = receiverId;
        validateSelf();
    }
}
