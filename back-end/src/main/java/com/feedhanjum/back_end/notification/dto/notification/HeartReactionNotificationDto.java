package com.feedhanjum.back_end.notification.dto.notification;

import com.feedhanjum.back_end.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "피드백 하트 반응 알림")
@Getter
public class HeartReactionNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = NotificationType.HEART_REACTION)
    private final String type = NotificationType.HEART_REACTION;
    @Schema(description = "보낸 사람 이름")
    private String senderName;
    @Schema(description = "팀 이름")
    private String teamName;


}
