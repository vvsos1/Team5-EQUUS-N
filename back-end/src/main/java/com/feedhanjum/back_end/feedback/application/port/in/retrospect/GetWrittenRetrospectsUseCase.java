package com.feedhanjum.back_end.feedback.application.port.in.retrospect;

import com.feedhanjum.back_end.feedback.application.port.in.retrospect.command.GetWrittenRetrospectsCommand;
import com.feedhanjum.back_end.feedback.domain.retrospect.Retrospect;
import org.springframework.data.domain.Page;

public interface GetWrittenRetrospectsUseCase {
    int PAGE_SIZE = 10;

    Page<Retrospect> getWrittenRetrospects(GetWrittenRetrospectsCommand command);
}
