package com.feedhanjum.back_end.team.domain;

import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Team {
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @OneToMany(mappedBy = "team")
    private final List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private final List<Schedule> schedules = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private Member leader;

    public Team(String name, Member leader, LocalDateTime startTime, LocalDateTime endTime, FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.leader = leader;
    }
}
