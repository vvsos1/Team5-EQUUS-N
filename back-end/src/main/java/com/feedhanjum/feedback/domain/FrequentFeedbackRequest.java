package com.feedhanjum.feedback.domain;

import com.feedhanjum.core.event.Events;
import com.feedhanjum.team.event.FrequentFeedbackRequestedEvent;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FrequentFeedbackRequest {
    public static final int MIN_REQUESTED_CONTENT_BYTE = 0;
    public static final int MAX_REQUESTED_CONTENT_BYTE = 400;

    private Long id;

    private final LocalDateTime createdAt;

    private final String requestedContent;

    private final FeedbackMember requester;

    private final AssociatedTeam team;

    private final FeedbackMember receiver;

    public FrequentFeedbackRequest(String requestedContent, FeedbackMember requester, AssociatedTeam team, FeedbackMember receiver, LocalDateTime createdAt) {
        this.requestedContent = requestedContent;
        this.requester = requester;
        this.team = team;
        this.receiver = receiver;
        this.createdAt = createdAt;
        Events.raise(new FrequentFeedbackRequestedEvent(requester.getId(), team.getId(), receiver.getId()));
    }

}
