package com.feedhanjum.feedback.application.port.in.feedback.command;

import com.feedhanjum.core.SelfValidating;
import com.feedhanjum.feedback.domain.feedback.FeedbackId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LikeFeedbackCommand extends SelfValidating<LikeFeedbackCommand> {
    @NotNull
    private final FeedbackId feedbackId;
    @NotNull
    private final Long memberId;

    public LikeFeedbackCommand(FeedbackId feedbackId, Long memberId) {
        this.feedbackId = feedbackId;
        this.memberId = memberId;
        validateSelf();
    }
}
