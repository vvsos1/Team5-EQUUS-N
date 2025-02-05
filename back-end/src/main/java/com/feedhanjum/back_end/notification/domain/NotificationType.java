package com.feedhanjum.back_end.notification.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum NotificationType {
    FEEDBACK_RECEIVE("feedbackReceive"),
    HEART_REACTION("heartReaction"),
    REGULAR_FEEDBACK_REQUEST("regularFeedbackRequest"),
    FEEDBACK_REPORT_CREATE("feedbackReportCreate"),
    UNREAD_FEEDBACK_EXIST("unreadFeedbackExist"),
    TEAM_LEADER_CHANGE("teamLeaderChange"),
    SCHEDULE_CREATE("scheduleCreate"),
    FREQUENT_FEEDBACK_REQUEST("frequentFeedbackRequest");


    @Getter(onMethod_ = @JsonValue)
    private final String description;

    NotificationType(String description) {
        this.description = description;
    }


}
