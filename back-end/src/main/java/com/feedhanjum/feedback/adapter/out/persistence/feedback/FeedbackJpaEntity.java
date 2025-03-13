package com.feedhanjum.feedback.adapter.out.persistence.feedback;

import com.feedhanjum.feedback.domain.AssociatedTeam;
import com.feedhanjum.feedback.domain.FeedbackMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "feedbacks")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class FeedbackJpaEntity {

    @Id
    @Column(name = "feedback_id")
    private Long id;

    @Column(name = "feedback_type")
    private String feedbackType;

    @Column(name = "feedback_feeling")
    private String feedbackFeeling;

    @Column(name = "subjective_feedback")
    private String subjectiveFeedback;

    @Column(name = "liked")
    private boolean liked = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "sender_id")),
            @AttributeOverride(name = "name", column = @Column(name = "sender_name")),
            @AttributeOverride(name = "email", column = @Column(name = "sender_email")),
            @AttributeOverride(name = "profileImage.backgroundColor", column = @Column(name = "sender_background_color")),
            @AttributeOverride(name = "profileImage.image", column = @Column(name = "sender_image")),
    })
    private FeedbackMember sender;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "receiver_id")),
            @AttributeOverride(name = "name", column = @Column(name = "receiver_name")),
            @AttributeOverride(name = "email", column = @Column(name = "receiver_email")),
            @AttributeOverride(name = "profileImage.backgroundColor", column = @Column(name = "receiver_background_color")),
            @AttributeOverride(name = "profileImage.image", column = @Column(name = "receiver_image")),
    })
    private FeedbackMember receiver;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "team_id")),
            @AttributeOverride(name = "name", column = @Column(name = "team_name")),
    })
    private AssociatedTeam team;

    // 객관식 피드백
    @Column(name = "objective_feedbacks", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<String> objectiveFeedbacks;

    FeedbackJpaEntity(Long id, String feedbackType, String feedbackFeeling, Set<String> objectiveFeedbacks, String subjectiveFeedback, boolean liked, FeedbackMember sender, FeedbackMember receiver, AssociatedTeam team, LocalDateTime createdAt) {
        this.id = id;
        this.feedbackType = feedbackType;
        this.feedbackFeeling = feedbackFeeling;
        this.objectiveFeedbacks = objectiveFeedbacks;
        this.subjectiveFeedback = subjectiveFeedback;
        this.liked = liked;
        this.sender = sender;
        this.receiver = receiver;
        this.team = team;
        this.createdAt = createdAt;
    }

}
