package com.feedhanjum.feedback.application.port.in.report.command;


import com.feedhanjum.core.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateFeedbackReportCommand extends SelfValidating<CreateFeedbackReportCommand> {
    @NotNull
    private final Long memberId;

    public CreateFeedbackReportCommand(Long memberId) {
        this.memberId = memberId;
        validateSelf();
    }
}
