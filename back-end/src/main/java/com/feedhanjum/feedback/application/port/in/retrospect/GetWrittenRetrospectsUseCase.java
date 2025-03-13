package com.feedhanjum.feedback.application.port.in.retrospect;

import com.feedhanjum.feedback.application.port.in.retrospect.command.GetWrittenRetrospectsCommand;
import com.feedhanjum.feedback.domain.retrospect.Retrospect;
import org.springframework.data.domain.Page;

public interface GetWrittenRetrospectsUseCase {
    int PAGE_SIZE = 10;

    Page<Retrospect> getWrittenRetrospects(GetWrittenRetrospectsCommand command);
}
