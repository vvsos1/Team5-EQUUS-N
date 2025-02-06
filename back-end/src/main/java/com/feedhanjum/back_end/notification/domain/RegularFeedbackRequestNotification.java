package com.feedhanjum.back_end.notification.domain;


import com.feedhanjum.back_end.schedule.domain.RegularFeedbackRequest;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(NotificationType.REGULAR_FEEDBACK_REQUEST)
public class RegularFeedbackRequestNotification extends InAppNotification {
    private String scheduleName;
    private Long scheduleId;
    private Long teamId;

    public RegularFeedbackRequestNotification(RegularFeedbackRequest request) {
        super(request.getScheduleMember().getMember());
        this.scheduleName = request.getScheduleMember().getSchedule().getName();
        this.scheduleId = request.getScheduleMember().getId();
        this.teamId = request.getScheduleMember().getSchedule().getTeam().getId();
    }
}
