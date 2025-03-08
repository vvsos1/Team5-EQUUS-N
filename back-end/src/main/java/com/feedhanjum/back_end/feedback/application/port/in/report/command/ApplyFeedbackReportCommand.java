package com.feedhanjum.back_end.feedback.application.port.in.report.command;


import com.feedhanjum.back_end.core.SelfValidating;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ApplyFeedbackReportCommand extends SelfValidating<ApplyFeedbackReportCommand> {
    @NotNull
    private final Long memberId;
    @NotNull
    private final FeedbackId feedbackId;

    public ApplyFeedbackReportCommand(Long memberId, FeedbackId feedbackId) {
        this.memberId = memberId;
        this.feedbackId = feedbackId;
        validateSelf();
    }
}
