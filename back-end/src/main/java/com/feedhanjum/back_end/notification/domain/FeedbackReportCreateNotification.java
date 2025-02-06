package com.feedhanjum.back_end.notification.domain;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.team.domain.Team;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(NotificationType.FEEDBACK_REPORT_CREATE)
public class FeedbackReportCreateNotification extends InAppNotification {
    private String teamName;
    private String receiverName;

    public FeedbackReportCreateNotification(Member receiver, Team team) {
        super(receiver);
        this.teamName = team.getName();
        this.receiverName = receiver.getName();
    }
}
