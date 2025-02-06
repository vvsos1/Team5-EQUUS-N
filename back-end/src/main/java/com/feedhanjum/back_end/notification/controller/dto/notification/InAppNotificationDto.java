package com.feedhanjum.back_end.notification.controller.dto.notification;

import com.feedhanjum.back_end.notification.domain.*;
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

    protected InAppNotificationDto(Long notificationId, Long receiverId, LocalDateTime createdAt, boolean isRead) {
        this.notificationId = notificationId;
        this.receiverId = receiverId;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    public static InAppNotificationDto from(InAppNotification inAppNotification) {
        if (inAppNotification instanceof FeedbackReceiveNotification n) {
            return FeedbackReceiveNotificationDto.from(n);
        } else if (inAppNotification instanceof FeedbackReportCreateNotification n) {
            return FeedbackReportCreateNotificationDto.from(n);
        } else if (inAppNotification instanceof FrequentFeedbackRequestNotification n) {
            return FrequentFeedbackRequestNotificationDto.from(n);
        } else if (inAppNotification instanceof HeartReactionNotification n) {
            return HeartReactionNotificationDto.from(n);
        } else if (inAppNotification instanceof RegularFeedbackRequestNotification n) {
            return RegularFeedbackRequestNotificationDto.from(n);
        } else if (inAppNotification instanceof ScheduleCreateNotification n) {
            return ScheduleCreateNotificationDto.from(n);
        } else if (inAppNotification instanceof TeamLeaderChangeNotification n) {
            return TeamLeaderChangeNotificationDto.from(n);
        } else if (inAppNotification instanceof UnreadFeedbackExistNotification n) {
            return UnreadFeedbackExistNotificationDto.from(n);
        }
        throw new RuntimeException("Unreachable");
    }
}
