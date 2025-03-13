package com.feedhanjum.feedback.adapter.out.persistence.feedback;

import com.feedhanjum.feedback.application.port.out.feedback.FeedbackReportLock;
import lombok.Getter;

@Getter
class FeedbackReportJpaLock implements FeedbackReportLock {
    private final Long memberId;
    private final FeedbackReportJpaEntity entity;

    FeedbackReportJpaLock(FeedbackReportJpaEntity entity) {
        this.memberId = entity.getMemberId();
        this.entity = entity;
    }
}
