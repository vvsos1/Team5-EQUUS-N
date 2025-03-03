package com.feedhanjum.back_end.feedback.domain;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.schedule.domain.Schedule;
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

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Member requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_member_id")
    private Member receiver;

    public RegularFeedbackRequest(LocalDateTime createdAt, Member requester, Schedule schedule, Member receiver) {
        this.createdAt = createdAt;
        this.requester = requester;
        this.schedule = schedule;
        this.receiver = receiver;
    }
}
