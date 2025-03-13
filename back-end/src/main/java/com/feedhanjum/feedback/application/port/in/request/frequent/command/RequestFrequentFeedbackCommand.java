package com.feedhanjum.feedback.application.port.in.request.frequent.command;

import com.feedhanjum.core.SelfValidating;
import com.feedhanjum.core.constraints.ByteLength;
import com.feedhanjum.feedback.domain.FrequentFeedbackRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RequestFrequentFeedbackCommand extends SelfValidating<RequestFrequentFeedbackCommand> {
    @NotNull
    private final Long requesterId;
    @NotNull
    private final Long teamId;
    @NotNull
    private final Long receiverId;

    @ByteLength(min = FrequentFeedbackRequest.MIN_REQUESTED_CONTENT_BYTE, max = FrequentFeedbackRequest.MAX_REQUESTED_CONTENT_BYTE)
    private final String requestedContent;

    public RequestFrequentFeedbackCommand(Long requesterId, Long teamId, Long receiverId, String requestedContent) {
        this.requesterId = requesterId;
        this.teamId = teamId;
        this.receiverId = receiverId;
        this.requestedContent = requestedContent;
        validateSelf();
    }
}
