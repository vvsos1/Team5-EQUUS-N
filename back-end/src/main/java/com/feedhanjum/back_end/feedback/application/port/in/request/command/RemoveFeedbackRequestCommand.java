package com.feedhanjum.back_end.feedback.application.port.in.request.command;

import com.feedhanjum.back_end.core.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RemoveFeedbackRequestCommand extends SelfValidating<RemoveFeedbackRequestCommand> {
    @NotNull
    private final Long teamId;

    @NotNull
    private final Long memberId;

    public RemoveFeedbackRequestCommand(Long teamId, Long memberId) {
        this.teamId = teamId;
        this.memberId = memberId;
        validateSelf();
    }
}
