package com.feedhanjum.feedback.adapter.out.persistence.feedback;

import com.feedhanjum.feedback.domain.FeedbackReport;
import org.springframework.stereotype.Component;

@Component
class FeedbackReportMapper {
    public FeedbackReportJpaEntity fromDomain(FeedbackReport domain) {
        return new FeedbackReportJpaEntity(
                domain.getMemberId(),
                domain.getFeedbackCount(),
                domain.getRequiredFeedbackCount(),
                domain.getOverviews(),
                domain.getAllKeywords()
        );
    }

    public FeedbackReport toDomain(FeedbackReportJpaEntity entity) {
        return new FeedbackReport(
                entity.getMemberId(),
                entity.getFeedbackCount(),
                entity.getRequiredFeedbackCount(),
                entity.getOverviews(),
                entity.getAllKeywords()
        );
    }
}
