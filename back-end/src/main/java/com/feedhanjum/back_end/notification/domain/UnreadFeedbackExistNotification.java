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
@DiscriminatorValue(NotificationType.UNREAD_FEEDBACK_EXIST)
public class UnreadFeedbackExistNotification extends InAppNotification {
    private String senderName;
    private String teamName;
    private Long teamId;

    public UnreadFeedbackExistNotification(Member receiver, Team team, FeedbackReceiveNotification notification) {
        super(receiver);
        this.senderName = notification.getSenderName();
        this.teamName = notification.getTeamName();
        this.teamId = team.getId();
    }
}
