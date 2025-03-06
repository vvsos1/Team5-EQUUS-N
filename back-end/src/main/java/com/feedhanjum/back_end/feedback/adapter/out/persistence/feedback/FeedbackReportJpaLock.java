package com.feedhanjum.back_end.feedback.adapter.out.persistence.feedback;

import com.feedhanjum.back_end.feedback.application.port.out.feedback.FeedbackReportLock;
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
