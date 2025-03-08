package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.retrospect.GetWrittenRetrospectsUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.retrospect.command.GetWrittenRetrospectsCommand;
import com.feedhanjum.back_end.feedback.application.port.out.retrospect.LoadWrittenRetrospectPort;
import com.feedhanjum.back_end.feedback.domain.retrospect.Retrospect;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class GetWrittenRetrospectsService implements GetWrittenRetrospectsUseCase {
    private final LoadWrittenRetrospectPort loadWrittenRetrospectPort;

    @Override
    public Page<Retrospect> getWrittenRetrospects(GetWrittenRetrospectsCommand command) {
        return loadWrittenRetrospectPort.loadWrittenRetrospects(command.getWriterId(), command.getTeamId(), command.getPage(), PAGE_SIZE, command.getDirection());
    }
}
