package com.feedhanjum.back_end.notification.domain;

import com.feedhanjum.back_end.team.domain.FrequentFeedbackRequest;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(NotificationType.FREQUENT_FEEDBACK_REQUEST)
public class FrequentFeedbackRequestNotification extends InAppNotification {
    private String senderName;
    private Long teamId;

    public FrequentFeedbackRequestNotification(FrequentFeedbackRequest request) {
        super(request.getTeamMember().getMember().getId());
        this.senderName = request.getRequester().getName();
        this.teamId = request.getTeamMember().getTeam().getId();
    }
}
