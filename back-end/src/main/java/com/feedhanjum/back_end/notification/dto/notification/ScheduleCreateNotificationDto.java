package com.feedhanjum.back_end.notification.dto.notification;

import com.feedhanjum.back_end.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "일정 생성 알림")
@Getter
public class ScheduleCreateNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = "scheduleCreate")
    private final NotificationType type = NotificationType.SCHEDULE_CREATE;
    @Schema(description = "생성된 일정이 속한 팀 이름")
    private String teamName;
    @Schema(description = "생성된 일정의 날짜")
    private LocalDateTime scheduleDate;
}
