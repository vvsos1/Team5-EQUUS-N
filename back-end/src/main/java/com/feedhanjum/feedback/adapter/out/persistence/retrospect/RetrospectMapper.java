package com.feedhanjum.feedback.adapter.out.persistence.retrospect;

import com.feedhanjum.feedback.domain.retrospect.Retrospect;
import com.feedhanjum.feedback.domain.retrospect.RetrospectId;
import org.springframework.stereotype.Component;

@Component
class RetrospectMapper {
    public RetrospectJpaEntity fromDomain(Retrospect retrospect) {
        return new RetrospectJpaEntity(
                retrospect.getId().getId(),
                retrospect.getTitle(),
                retrospect.getContent(),
                retrospect.getWriter(),
                retrospect.getTeam(),
                retrospect.getCreatedAt()
        );
    }

    public Retrospect toDomain(RetrospectJpaEntity entity) {
        return new Retrospect(
                new RetrospectId(entity.getId()),
                entity.getTitle(),
                entity.getContent(),
                entity.getWriter(),
                entity.getTeam(),
                entity.getCreatedAt()
        );
    }
}
