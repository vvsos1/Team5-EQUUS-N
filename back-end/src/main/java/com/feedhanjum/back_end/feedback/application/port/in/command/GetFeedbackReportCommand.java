package com.feedhanjum.back_end.feedback.application.port.in.command;


import com.feedhanjum.back_end.core.SelfValidating;
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
