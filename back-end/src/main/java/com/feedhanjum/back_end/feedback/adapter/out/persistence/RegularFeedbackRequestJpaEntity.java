package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RegularFeedbackRequestJpaEntity {
    @Id
    @Column(name = "regular_feedback_request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @Column(name = "requester_id")
    private Long requesterId;

    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "schedule_member_id")
    private Long receiverId;

    public RegularFeedbackRequestJpaEntity(LocalDateTime createdAt, Long requesterId, Long scheduleId, Long receiverId) {
        this.createdAt = createdAt;
        this.requesterId = requesterId;
        this.scheduleId = scheduleId;
        this.receiverId = receiverId;
    }

}