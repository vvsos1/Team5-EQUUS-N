package com.feedhanjum.back_end.feedback.event;

import jakarta.annotation.Nullable;

/**
 * @param endedTeamId 팀의 종료와 별개로 피드백이 최초로 20개 쌓였을 경우 피드백 리포트가 생성되므로, 이 때 해당 필드가 null이 된다.
 */
public record FeedbackReportCreatedEvent(Long receiverId, @Nullable Long endedTeamId) {
}
