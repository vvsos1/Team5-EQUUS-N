package com.feedhanjum.back_end.feedback.repository;

import com.feedhanjum.back_end.feedback.domain.QRegularFeedbackRequest;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RegularFeedbackRequestQueryRepository {
    private final QRegularFeedbackRequest regularFeedbackRequest = QRegularFeedbackRequest.regularFeedbackRequest;
    private final JPAQueryFactory queryFactory;


    public List<RegularFeedbackRequest> getRegularFeedbackRequests(Long receiverId, Long scheduleId) {
        return queryFactory.selectFrom(regularFeedbackRequest)
                .join(regularFeedbackRequest.requester).fetchJoin()
                .join(regularFeedbackRequest.receiver).fetchJoin()
                .join(regularFeedbackRequest.schedule).fetchJoin()
                .where(regularFeedbackRequest.receiver.id.eq(receiverId)
                        .and(regularFeedbackRequest.schedule.id.eq(scheduleId)))
                .fetch();
    }

    public Long getRegularFeedbackRequestCount(Long receiverId, Long scheduleId) {
        return queryFactory.select(regularFeedbackRequest.count())
                .from(regularFeedbackRequest)
                .where(regularFeedbackRequest.receiver.id.eq(receiverId)
                        .and(regularFeedbackRequest.schedule.id.eq(scheduleId)))
                .fetchOne();
    }
}
