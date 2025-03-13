package com.feedhanjum.feedback.application.port.in.report.command;


import com.feedhanjum.core.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GetFeedbackReportCommand extends SelfValidating<GetFeedbackReportCommand> {
    @NotNull
    private final Long memberId;

    public GetFeedbackReportCommand(Long memberId) {
        this.memberId = memberId;
        validateSelf();
    }
}
