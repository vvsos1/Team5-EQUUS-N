package com.feedhanjum.feedback.application.port.in.feedback.command;

import com.feedhanjum.core.SelfValidating;
import com.feedhanjum.feedback.domain.feedback.FeedbackId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UnlikeFeedbackCommand extends SelfValidating<UnlikeFeedbackCommand> {
    @NotNull
    private final FeedbackId feedbackId;
    @NotNull
    private final Long memberId;

    public UnlikeFeedbackCommand(FeedbackId feedbackId, Long memberId) {
        this.feedbackId = feedbackId;
        this.memberId = memberId;
        validateSelf();
    }
}
