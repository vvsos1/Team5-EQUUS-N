package com.feedhanjum.back_end.notification.controller.dto.notification;

import com.feedhanjum.back_end.notification.domain.FeedbackReportCreateNotification;
import com.feedhanjum.back_end.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "피드백 리포트 생성 알림")
@Getter
public class FeedbackReportCreateNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = NotificationType.FEEDBACK_REPORT_CREATE)
    private final String type = NotificationType.FEEDBACK_REPORT_CREATE;
    @Schema(description = "종료된 팀 이름. 처음으로 피드백 20개가 쌓였을 경우 팀과 별개로 피드백 리포트가 생성되므로, 이 때 본 필드는 null이 됨")
    @Nullable
    private final String teamName;
    @Schema(description = "받는 사람 이름")
    private final String receiverName;

    @Builder
    public FeedbackReportCreateNotificationDto(Long notificationId, Long receiverId, LocalDateTime createdAt, boolean isRead, @Nullable String teamName, String receiverName) {
        super(notificationId, receiverId, createdAt, isRead);
        this.teamName = teamName;
        this.receiverName = receiverName;
    }

    public static FeedbackReportCreateNotificationDto from(FeedbackReportCreateNotification notification) {
        return builder()
                .notificationId(notification.getId())
                .receiverId(notification.getReceiverId())
                .createdAt(notification.getCreatedAt())
                .isRead(notification.isRead())
                .teamName(notification.getTeamName())
                .receiverName(notification.getReceiverName())
                .build();
    }

}
