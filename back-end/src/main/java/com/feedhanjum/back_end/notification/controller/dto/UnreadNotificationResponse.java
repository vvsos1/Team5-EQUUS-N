package com.feedhanjum.back_end.notification.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "안읽은 알림 존재 여부 응답")
public record UnreadNotificationResponse(
        @Schema(description = "안읽은 알림 존재 여부")
        Boolean hasUnreadNotification
) {
}
