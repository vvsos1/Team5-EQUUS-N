package com.feedhanjum.notification.domain;

import com.feedhanjum.member.domain.Member;
import com.feedhanjum.schedule.domain.Schedule;
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

    public ScheduleCreateNotification(Member receiver, Schedule schedule) {
        super(receiver.getId());
        this.teamName = schedule.getTeam().getName();
        this.scheduleDate = schedule.getStartTime().toLocalDate().atStartOfDay();
        this.teamId = schedule.getTeam().getId();
    }
}
