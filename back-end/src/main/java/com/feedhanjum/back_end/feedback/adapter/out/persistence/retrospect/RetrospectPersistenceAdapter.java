package com.feedhanjum.back_end.feedback.adapter.out.persistence.retrospect;

import com.feedhanjum.back_end.feedback.application.port.out.retrospect.SaveRetrospectPort;
import com.feedhanjum.back_end.feedback.domain.retrospect.Retrospect;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class RetrospectPersistenceAdapter implements SaveRetrospectPort {
    private final RetrospectJpaEntityRepository retrospectJpaEntityRepository;
    private final RetrospectMapper retrospectMapper;

    @Override
    public void save(Retrospect retrospect) {
        var entity = retrospectMapper.fromDomain(retrospect);
        retrospectJpaEntityRepository.save(entity);
    }
}
