package com.feedhanjum.back_end.feedback.repository;

import com.feedhanjum.back_end.feedback.domain.QRegularFeedbackRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RegularFeedbackRequestQueryRepository {
    private final QRegularFeedbackRequest regularFeedbackRequest = QRegularFeedbackRequest.regularFeedbackRequest;
    private final JPAQueryFactory queryFactory;


    public Long getRegularFeedbackRequestCount(Long receiverId, Long scheduleId) {
        return queryFactory.select(regularFeedbackRequest.count())
                .from(regularFeedbackRequest)
                .where(regularFeedbackRequest.receiver.id.eq(receiverId)
                        .and(regularFeedbackRequest.schedule.id.eq(scheduleId)))
                .fetchOne();
    }
}
