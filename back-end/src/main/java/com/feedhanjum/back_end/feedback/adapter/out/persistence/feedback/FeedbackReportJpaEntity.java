package com.feedhanjum.back_end.feedback.adapter.out.persistence.feedback;

import com.feedhanjum.back_end.feedback.domain.FeedbackReport;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class FeedbackReportJpaEntity {
    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "feedback_count")
    private Integer feedbackCount;

    @Column(name = "required_feedback_count")
    private Integer requiredFeedbackCount;

    @Column(name = "overviews", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<FeedbackReport.CategoryCount> overviews;

    @Column(name = "allKeywords", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<FeedbackReport.KeywordCount> allKeywords;

    FeedbackReportJpaEntity(Long memberId, Integer feedbackCount, Integer requiredFeedbackCount, List<FeedbackReport.CategoryCount> overviews, List<FeedbackReport.KeywordCount> allKeywords) {
        this.memberId = memberId;
        this.feedbackCount = feedbackCount;
        this.requiredFeedbackCount = requiredFeedbackCount;
        this.overviews = overviews;
        this.allKeywords = allKeywords;
    }
}
