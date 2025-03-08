package com.feedhanjum.back_end.feedback.application.port.in.feedback.command;

import com.feedhanjum.back_end.core.SelfValidating;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class GetSentFeedbacksCommand extends SelfValidating<GetSentFeedbacksCommand> {
    @NotNull
    private final Long senderId;

    @Nullable
    private final Long teamId;

    @NotNull
    private final boolean filterHelpful;

    @PositiveOrZero
    @NotNull
    private final int page;

    @NotNull
    private final Sort.Direction direction;

    public GetSentFeedbacksCommand(Long senderId, @Nullable Long teamId, boolean filterHelpful, int page, Sort.Direction direction) {
        this.senderId = senderId;
        this.teamId = teamId;
        this.filterHelpful = filterHelpful;
        this.page = page;
        this.direction = direction;
        validateSelf();
    }
}
