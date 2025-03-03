package com.feedhanjum.back_end.feedback.application.port.in.command;

import com.feedhanjum.back_end.core.SelfValidating;
import com.feedhanjum.back_end.feedback.domain.FeedbackId;
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
