package com.feedhanjum.back_end.feedback.application.port.in.command;

import com.feedhanjum.back_end.core.SelfValidating;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
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
