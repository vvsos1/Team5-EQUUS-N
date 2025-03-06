package com.feedhanjum.back_end.feedback.application.port.in.command;

import com.feedhanjum.back_end.core.SelfValidating;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class GetReceivedFeedbacksCommand extends SelfValidating<GetReceivedFeedbacksCommand> {
    @NotNull
    private final Long receiverId;

    @Nullable
    private final Long teamId;

    @NotNull
    private final boolean filterHelpful;

    @PositiveOrZero
    @NotNull
    private final int page;

    @NotNull
    private final Sort.Direction direction;

    public GetReceivedFeedbacksCommand(Long receiverId, @Nullable Long teamId, boolean filterHelpful, int page, Sort.Direction direction) {
        this.receiverId = receiverId;
        this.teamId = teamId;
        this.filterHelpful = filterHelpful;
        this.page = page;
        this.direction = direction;
        validateSelf();
    }
}
