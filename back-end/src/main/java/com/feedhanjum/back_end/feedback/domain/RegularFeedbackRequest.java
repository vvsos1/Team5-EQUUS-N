package com.feedhanjum.back_end.feedback.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegularFeedbackRequest {
    private Long id;

    private final LocalDateTime createdAt;

    private final FeedbackMember requester;

    private final AssociatedSchedule schedule;

    private final FeedbackMember receiver;

    public RegularFeedbackRequest(LocalDateTime createdAt, FeedbackMember requester, AssociatedSchedule schedule, FeedbackMember receiver) {
        this.createdAt = createdAt;
        this.requester = requester;
        this.schedule = schedule;
        this.receiver = receiver;
    }
}
