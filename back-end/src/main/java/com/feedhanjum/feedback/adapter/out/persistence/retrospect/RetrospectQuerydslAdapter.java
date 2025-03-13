package com.feedhanjum.feedback.adapter.out.persistence.retrospect;

import com.feedhanjum.feedback.application.port.out.retrospect.LoadWrittenRetrospectPort;
import com.feedhanjum.feedback.domain.retrospect.Retrospect;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class RetrospectQuerydslAdapter implements LoadWrittenRetrospectPort {
    private final QRetrospectJpaEntity retrospect = QRetrospectJpaEntity.retrospectJpaEntity;
    private final ComparableExpressionBase<?> sortProperty = retrospect.id;
    private final JPAQueryFactory queryFactory;
    private final RetrospectMapper retrospectMapper;

    @Override
    public Page<Retrospect> loadWrittenRetrospects(long writerId, @Nullable Long teamId, int page, int pageSize, Sort.Direction sortOrder) {
        var pageable = PageRequest.of(page, pageSize);

        var predicate = new BooleanBuilder();
        predicate.and(retrospect.writer.id.eq(writerId));
        if (teamId != null) {
            predicate.and(retrospect.team.id.eq(teamId));
        }

        var result = queryFactory
                .selectFrom(retrospect)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sortOrder == Sort.Direction.ASC ? sortProperty.asc() : sortProperty.desc())
                .fetch();
        var total = queryFactory.select(retrospect.count())
                .from(retrospect)
                .where(predicate)
                .fetchOne();
        if (total == null) {
            total = (long) result.size();
        }
        return new PageImpl<>(result.stream().map(retrospectMapper::toDomain).toList(),
                pageable,
                total);
    }
}
