package com.feedhanjum.back_end.notification.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(NotificationType.SCHEDULE_CREATE)
public class ScheduleCreateNotification extends InAppNotification {
    private String teamName;
    private LocalDateTime scheduleDate;
    private Long teamId;

}
