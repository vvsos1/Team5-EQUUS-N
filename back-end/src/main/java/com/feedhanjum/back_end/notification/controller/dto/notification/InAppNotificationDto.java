package com.feedhanjum.back_end.notification.controller.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(subTypes = {FeedbackReceiveNotificationDto.class, FeedbackReportCreateNotificationDto.class, FrequentFeedbackRequestNotificationDto.class, HeartReactionNotificationDto.class, RegularFeedbackRequestNotificationDto.class, ScheduleCreateNotificationDto.class, TeamLeaderChangeNotificationDto.class, UnreadFeedbackExistNotificationDto.class})
@Getter
public abstract class InAppNotificationDto {
    @Schema(description = "알림 ID")
    protected Long notificationId;
    @Schema(description = "받는 사람 ID")
    protected Long receiverId;
    @Schema(description = "알림 생성 시간")
    protected LocalDateTime createdAt;
    @Schema(description = "읽음 여부")
    private boolean isRead;
}
