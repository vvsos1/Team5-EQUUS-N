package com.feedhanjum.back_end.team.domain;

import com.feedhanjum.back_end.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FrequentFeedbackRequest {
    public static final int MIN_REQUESTED_CONTENT_BYTE = 0;
    public static final int MAX_REQUESTED_CONTENT_BYTE = 400;
    @Id
    @Column(name = "frequent_feedback_request_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime createdAt;

    private String requestedContent;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Member requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    public FrequentFeedbackRequest(String requestedContent, TeamMember teamMember, Member requester) {
        this.requestedContent = requestedContent;
        this.requester = requester;
        this.createdAt = LocalDateTime.now();
        setTeamMember(teamMember);
    }

    private void setTeamMember(TeamMember teamMember) {
        if (this.teamMember != null) {
            this.teamMember.getFrequentFeedbackRequests().remove(this);
        }
        this.teamMember = teamMember;
        if (teamMember != null && !teamMember.getFrequentFeedbackRequests().contains(this)) {
            teamMember.getFrequentFeedbackRequests().add(this);
        }
    }

}
