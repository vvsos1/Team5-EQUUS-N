package com.feedhanjum.back_end.notification.controller.dto.notification;

import com.feedhanjum.back_end.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "미확인 피드백 존재 알림")
@Getter
public class UnreadFeedbackExistNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = NotificationType.UNREAD_FEEDBACK_EXIST)
    private final String type = NotificationType.UNREAD_FEEDBACK_EXIST;
    @Schema(description = "미확인 피드백의 발송자 이름")
    private String senderName;
    @Schema(description = "미확인 피드백의 팀 이름")
    private String teamName;
    @Schema(description = "미확인 피드백의 팀 ID")
    private Long teamId;
}
