package com.feedhanjum.back_end.notification.controller.dto.notification;

import com.feedhanjum.back_end.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "수시 피드백 요청 알림")
@Getter
public class FrequentFeedbackRequestNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = NotificationType.FREQUENT_FEEDBACK_REQUEST)
    private final String type = NotificationType.FREQUENT_FEEDBACK_REQUEST;
    @Schema(description = "요청자 이름")
    private String senderName;
    @Schema(description = "팀 id")
    private Long teamId;
}
