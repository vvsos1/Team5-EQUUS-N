package com.feedhanjum.back_end.team.domain;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.team.exception.TeamLeaderMustExistException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Team {
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private Member leader;

    public Team(String name, Member leader, LocalDate startDate, LocalDate endDate, FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leader = leader;
    }

    public void changeLeader(Member newLeader) {
        if (newLeader == null) {
            throw new TeamLeaderMustExistException("팀 리더는 반드시 존재하는 사용자여야 합니다.");
        }
        this.leader = newLeader;
    }

    public void updateInfo(String name, LocalDate startDate, LocalDate endDate, FeedbackType feedbackType) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.feedbackType = feedbackType;
    }
}
