package com.feedhanjum.back_end.notification.controller.dto.notification;


import com.feedhanjum.back_end.notification.domain.NotificationType;
import com.feedhanjum.back_end.notification.domain.RegularFeedbackRequestNotification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "정기 피드백 작성 요청 알림")
@Getter
public class RegularFeedbackRequestNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = NotificationType.REGULAR_FEEDBACK_REQUEST)
    private final String type = NotificationType.REGULAR_FEEDBACK_REQUEST;
    @Schema(description = "연관된 일정 이름")
    private String scheduleName;
    @Schema(description = "연관된 일정 ID")
    private Long scheduleId;

    @Builder
    public RegularFeedbackRequestNotificationDto(Long notificationId, Long receiverId, LocalDateTime createdAt, boolean isRead, String scheduleName, Long scheduleId) {
        super(notificationId, receiverId, createdAt, isRead);
        this.scheduleName = scheduleName;
        this.scheduleId = scheduleId;
    }

    public static RegularFeedbackRequestNotificationDto from(RegularFeedbackRequestNotification notification) {
        return builder()
                .notificationId(notification.getId())
                .receiverId(notification.getReceiverId())
                .createdAt(notification.getCreatedAt())
                .isRead(notification.isRead())
                .scheduleId(notification.getScheduleId())
                .scheduleName(notification.getScheduleName())
                .build();
    }
}

