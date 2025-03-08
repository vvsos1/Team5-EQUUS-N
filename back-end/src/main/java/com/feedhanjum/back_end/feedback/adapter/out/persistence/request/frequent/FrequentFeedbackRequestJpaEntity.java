package com.feedhanjum.back_end.feedback.adapter.out.persistence.request.frequent;

import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "regular_feedback_requests",
        uniqueConstraints = @UniqueConstraint(columnNames = {"requester_id", "team_id", "receiver_id"}))
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class FrequentFeedbackRequestJpaEntity {
    @Id
    @Column(name = "regular_feedback_request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requested_content", length = 500)
    private String requestedContent;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "requester_id")),
            @AttributeOverride(name = "name", column = @Column(name = "requester_name")),
            @AttributeOverride(name = "email", column = @Column(name = "requester_email")),
            @AttributeOverride(name = "profileImage.backgroundColor", column = @Column(name = "requester_background_color")),
            @AttributeOverride(name = "profileImage.image", column = @Column(name = "requester_image")),
    })
    private FeedbackMember requester;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "team_id")),
            @AttributeOverride(name = "name", column = @Column(name = "team_name")),
    })
    private AssociatedTeam team;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "receiver_id")),
            @AttributeOverride(name = "name", column = @Column(name = "receiver_name")),
            @AttributeOverride(name = "email", column = @Column(name = "receiver_email")),
            @AttributeOverride(name = "profileImage.backgroundColor", column = @Column(name = "receiver_background_color")),
            @AttributeOverride(name = "profileImage.image", column = @Column(name = "receiver_image")),
    })
    private FeedbackMember receiver;

    private LocalDateTime createdAt;

    public FrequentFeedbackRequestJpaEntity(String requestedContent, FeedbackMember requester, AssociatedTeam team, FeedbackMember receiver, LocalDateTime createdAt) {
        this.requestedContent = requestedContent;
        this.requester = requester;
        this.team = team;
        this.receiver = receiver;
        this.createdAt = createdAt;
    }
}