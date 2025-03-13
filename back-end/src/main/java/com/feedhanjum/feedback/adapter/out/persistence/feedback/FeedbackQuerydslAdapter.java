package com.feedhanjum.feedback.adapter.out.persistence.feedback;

import com.feedhanjum.feedback.application.port.out.feedback.LoadReceivedFeedbackPort;
import com.feedhanjum.feedback.application.port.out.feedback.LoadSentFeedbackPort;
import com.feedhanjum.feedback.dto.ReceivedFeedbackDto;
import com.feedhanjum.feedback.dto.SentFeedbackDto;
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
class FeedbackQuerydslAdapter implements LoadSentFeedbackPort, LoadReceivedFeedbackPort {
    private final QFeedbackJpaEntity feedback = QFeedbackJpaEntity.feedbackJpaEntity;
    private final ComparableExpressionBase<?> sortProperty = feedback.id;
    private final JPAQueryFactory queryFactory;
    private final FeedbackMapper feedbackMapper;

    @Override
    public Page<SentFeedbackDto> loadSentFeedback(long senderId, @Nullable Long teamId, boolean filterHelpful, int page, int pageSize, Sort.Direction sortOrder) {
        var pageable = PageRequest.of(page, pageSize);

        var predicate = new BooleanBuilder();
        predicate.and(feedback.sender.id.eq(senderId));
        if (teamId != null) {
            predicate.and(feedback.team.id.eq(teamId));
        }
        if (filterHelpful) {
            predicate.and(feedback.liked.isTrue());
        }

        var result = queryFactory
                .selectFrom(feedback)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sortOrder == Sort.Direction.ASC ? sortProperty.asc() : sortProperty.desc())
                .fetch();
        var total = queryFactory.select(feedback.count())
                .from(feedback)
                .where(predicate)
                .fetchOne();
        if (total == null) {
            total = (long) result.size();
        }
        return new PageImpl<>(result.stream().map(feedbackMapper::toSentFeedbackDto).toList(),
                pageable,
                total);
    }

    @Override
    public Page<ReceivedFeedbackDto> loadReceivedFeedback(long receiverId, @Nullable Long teamId, boolean filterHelpful, int page, int pageSize, Sort.Direction sortOrder) {
        var pageable = PageRequest.of(page, pageSize);

        var predicate = new BooleanBuilder();
        predicate.and(feedback.receiver.id.eq(receiverId));
        if (teamId != null) {
            predicate.and(feedback.team.id.eq(teamId));
        }
        if (filterHelpful) {
            predicate.and(feedback.liked.isTrue());
        }
        var result = queryFactory
                .selectFrom(feedback)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sortOrder == Sort.Direction.ASC ? sortProperty.asc() : sortProperty.desc())
                .fetch();
        var total = queryFactory.select(feedback.count())
                .from(feedback)
                .where(predicate)
                .fetchOne();
        if (total == null) {
            total = (long) result.size();
        }
        return new PageImpl<>(result.stream().map(feedbackMapper::toReceivedFeedbackDto).toList(),
                pageable,
                total);
    }
}
