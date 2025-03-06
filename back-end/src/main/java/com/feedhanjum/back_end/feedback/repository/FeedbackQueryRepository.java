package com.feedhanjum.back_end.feedback.repository;

import com.feedhanjum.back_end.feedback.domain.feedback.QFeedback;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class FeedbackQueryRepository {
    private final com.feedhanjum.back_end.feedback.domain.feedback.QFeedback feedback = QFeedback.feedback;
    private final ComparableExpressionBase<?> sortProperty = feedback.createdAt;
    private final JPAQueryFactory queryFactory;

    public FeedbackQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    public Long findReceivedFeedbackCount(Long receiverId) {
        Objects.requireNonNull(receiverId);

        return queryFactory
                .select(feedback.count())
                .from(feedback)
                .where(feedback.receiver.id.eq(receiverId))
                .fetchOne();
    }

    public Long findSentFeedbackCount(Long senderId) {
        Objects.requireNonNull(senderId);

        return queryFactory
                .select(feedback.count())
                .from(feedback)
                .where(feedback.sender.id.eq(senderId))
                .fetchOne();
    }


}
