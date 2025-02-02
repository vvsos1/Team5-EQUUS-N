package com.feedhanjum.back_end.schedule.domain;

import com.feedhanjum.back_end.team.domain.Team;
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
public class Schedule {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "schedule")
    private final List<ScheduleMember> scheduleMembers = new ArrayList<>();

    public Schedule(String name, LocalDateTime startTime, LocalDateTime endTime, Team team) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.team = team;
    }

    public boolean isEnd() {
        return LocalDateTime.now().isAfter(endTime);
    }

}
