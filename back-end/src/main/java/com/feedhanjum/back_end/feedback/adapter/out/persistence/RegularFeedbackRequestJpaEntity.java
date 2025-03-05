package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import com.feedhanjum.back_end.feedback.domain.AssociatedSchedule;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "regular_feedback_requests")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RegularFeedbackRequestJpaEntity {
    @Id
    @Column(name = "regular_feedback_request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


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
            @AttributeOverride(name = "id", column = @Column(name = "schedule_id")),
            @AttributeOverride(name = "name", column = @Column(name = "schedule_name")),
            @AttributeOverride(name = "endTime", column = @Column(name = "schedule_end_time")),
            @AttributeOverride(name = "teamId", column = @Column(name = "team_id")),
    })
    private AssociatedSchedule schedule;

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

    public RegularFeedbackRequestJpaEntity(FeedbackMember requester, AssociatedSchedule schedule, FeedbackMember receiver, LocalDateTime createdAt) {
        this.requester = requester;
        this.schedule = schedule;
        this.receiver = receiver;
        this.createdAt = createdAt;
    }
}