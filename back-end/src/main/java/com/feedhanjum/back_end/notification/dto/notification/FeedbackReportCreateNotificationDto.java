package com.feedhanjum.back_end.notification.dto.notification;

import com.feedhanjum.back_end.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "피드백 리포트 생성 알림")
@Getter
public class FeedbackReportCreateNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = "feedbackReportCreate")
    private final NotificationType type = NotificationType.FEEDBACK_REPORT_CREATE;
    @Schema(description = "종료된 팀 이름")
    private String teamName;
    @Schema(description = "받는 사람 이름")
    private String receiverName;


}
