package com.feedhanjum.back_end.feedback.application.port.in.request.frequent.command;

import com.feedhanjum.back_end.core.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GetFrequentFeedbackRequestsCommand extends SelfValidating<GetFrequentFeedbackRequestsCommand> {
    @NotNull
    private final Long teamId;
    @NotNull
    private final Long receiverId;

    public GetFrequentFeedbackRequestsCommand(Long teamId, Long receiverId) {
        this.teamId = teamId;
        this.receiverId = receiverId;
        validateSelf();
    }
}
