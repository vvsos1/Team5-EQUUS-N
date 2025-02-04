package com.feedhanjum.back_end.notification.dto.notification;

import com.feedhanjum.back_end.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Schema(description = "피드백 수신 알림")
@Getter
public class FeedbackReceiveNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = "feedbackReceive")
    private final NotificationType type = NotificationType.FEEDBACK_RECEIVE;
    @Schema(description = "보낸 사람 이름")
    private String senderName;
    @Schema(description = "팀 이름")
    private String teamName;


}
