package com.feedhanjum.back_end.feedback.domain;

import com.feedhanjum.back_end.core.event.Events;
import com.feedhanjum.back_end.schedule.event.RegularFeedbackRequestCreatedEvent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RegularFeedbackRequest {
    @Id
    @Column(name = "regular_feedback_request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

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

    public RegularFeedbackRequest(LocalDateTime createdAt, FeedbackMember requester, AssociatedSchedule schedule, FeedbackMember receiver) {
        this.createdAt = createdAt;
        this.requester = requester;
        this.schedule = schedule;
        this.receiver = receiver;
        Events.raise(new RegularFeedbackRequestCreatedEvent(receiver.getId(), schedule.getId()));
    }
}
