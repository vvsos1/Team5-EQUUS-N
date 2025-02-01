package com.feedhanjum.back_end.team.domain;

import com.feedhanjum.back_end.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamMember {

    @Id
    @Column(name = "team_member_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "teamMember")
    private final List<FrequentFeedbackRequest> frequentFeedbackRequests = new ArrayList<>();

    public TeamMember(Team team, Member member) {
        setTeam(team);
        setMember(member);
    }

    private void setTeam(Team team) {
        if (this.team != null) {
            this.team.getTeamMembers().remove(this);
        }
        this.team = team;
        if (team != null && !team.getTeamMembers().contains(this)) {
            team.getTeamMembers().add(this);
        }
    }

    private void setMember(Member member) {
        if (this.member != null) {
            this.member.getTeamMembers().remove(this);
        }
        this.member = member;
        if (member != null && !member.getTeamMembers().contains(this)) {
            member.getTeamMembers().add(this);
        }
    }
}
