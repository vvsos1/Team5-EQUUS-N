package com.feedhanjum.feedback.application.port.in.request.regular.command;

import com.feedhanjum.core.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GetRegularFeedbackRequestListCommand extends SelfValidating<GetRegularFeedbackRequestListCommand> {
    @NotNull
    private final Long scheduleId;
    @NotNull
    private final Long receiverId;

    public GetRegularFeedbackRequestListCommand(Long scheduleId, Long receiverId) {
        this.scheduleId = scheduleId;
        this.receiverId = receiverId;
        validateSelf();
    }
}
