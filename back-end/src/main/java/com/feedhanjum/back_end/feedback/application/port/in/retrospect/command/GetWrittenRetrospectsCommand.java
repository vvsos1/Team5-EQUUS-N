package com.feedhanjum.back_end.feedback.application.port.in.retrospect.command;

import com.feedhanjum.back_end.core.SelfValidating;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class GetWrittenRetrospectsCommand extends SelfValidating<GetWrittenRetrospectsCommand> {
    @NotNull
    private final Long writerId;

    @Nullable
    private final Long teamId;

    @PositiveOrZero
    @NotNull
    private final int page;

    @NotNull
    private final Sort.Direction direction;

    public GetWrittenRetrospectsCommand(Long writerId, @Nullable Long teamId, int page, Sort.Direction direction) {
        this.writerId = writerId;
        this.teamId = teamId;
        this.page = page;
        this.direction = direction;
        validateSelf();
    }
}
