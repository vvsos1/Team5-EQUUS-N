package com.feedhanjum.back_end.notification.dto.notification;


import com.feedhanjum.back_end.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "정기 피드백 작성 요청 알림")
@Getter
public class RegularFeedbackRequestNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = NotificationType.REGULAR_FEEDBACK_REQUEST)
    private final String type = NotificationType.REGULAR_FEEDBACK_REQUEST;
    @Schema(description = "연관된 일정 이름")
    private String scheduleName;
    @Schema(description = "연관된 일정 ID")
    private Long scheduleId;

}
