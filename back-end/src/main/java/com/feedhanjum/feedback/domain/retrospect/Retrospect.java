package com.feedhanjum.feedback.domain.retrospect;

import com.feedhanjum.feedback.domain.AssociatedTeam;
import com.feedhanjum.feedback.domain.FeedbackMember;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Retrospect {
    public static final int MAX_TITLE_LENGTH = 50;
    public static final int MAX_CONTENT_BYTE = 400;

    private final RetrospectId id;

    private final String title;

    private final String content;

    private final FeedbackMember writer;

    private final AssociatedTeam team;

    private final LocalDateTime createdAt;

    public Retrospect(RetrospectId id, String title, String content, FeedbackMember writer, AssociatedTeam team, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.team = team;
        this.createdAt = createdAt;
    }
}
