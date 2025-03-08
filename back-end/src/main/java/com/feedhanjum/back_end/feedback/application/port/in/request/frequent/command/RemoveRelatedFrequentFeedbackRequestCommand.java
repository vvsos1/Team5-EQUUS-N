package com.feedhanjum.back_end.feedback.application.port.in.request.frequent.command;

import com.feedhanjum.back_end.core.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RemoveRelatedFrequentFeedbackRequestCommand extends SelfValidating<RemoveRelatedFrequentFeedbackRequestCommand> {
    @NotNull
    private final Long feedbackSenderId;
    @NotNull
    private final Long teamId;
    @NotNull
    private final Long feedbackReceiverId;

    public RemoveRelatedFrequentFeedbackRequestCommand(Long feedbackSenderId, Long teamId, Long feedbackReceiverId) {
        this.feedbackSenderId = feedbackSenderId;
        this.teamId = teamId;
        this.feedbackReceiverId = feedbackReceiverId;
        validateSelf();
    }
}
