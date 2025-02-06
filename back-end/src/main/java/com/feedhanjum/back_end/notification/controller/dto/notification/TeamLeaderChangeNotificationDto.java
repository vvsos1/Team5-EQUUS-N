package com.feedhanjum.back_end.notification.controller.dto.notification;

import com.feedhanjum.back_end.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "팀장이 나로 변경되었음을 알리는 알림")
@Getter
public class TeamLeaderChangeNotificationDto extends InAppNotificationDto {
    @Schema(description = "알림 타입", allowableValues = NotificationType.TEAM_LEADER_CHANGE)
    private final String type = NotificationType.TEAM_LEADER_CHANGE;
    @Schema(description = "팀장을 맡게 된 팀 이름")
    private String teamName;
    @Schema(description = "팀장을 맡게 된 팀 ID")
    private Long teamId;
}
