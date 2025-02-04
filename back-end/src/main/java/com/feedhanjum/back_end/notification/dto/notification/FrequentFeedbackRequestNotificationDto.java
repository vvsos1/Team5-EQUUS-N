package com.feedhanjum.back_end.notification.dto.notification;

import com.feedhanjum.back_end.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "수시 피드백 요청 알림")
@Getter
public class FrequentFeedbackRequestNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = "frequentFeedbackRequest")
    private final NotificationType type = NotificationType.FREQUENT_FEEDBACK_REQUEST;
    @Schema(description = "요청자들의 이름")
    private List<String> senderNames;
    @Schema(description = "팀 id")
    private Long teamId;
}
