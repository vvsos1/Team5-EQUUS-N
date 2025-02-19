package com.feedhanjum.back_end.team.domain;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.team.exception.TeamJoinTokenNotValidException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TeamJoinToken {
    public static final Integer EXPIRATION_HOURS = 24;
    @Getter
    @Id
    private String token;

    @Getter(AccessLevel.PRIVATE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Getter
    private LocalDateTime expireDate;

    private TeamJoinToken(String token, Team team) {
        this.token = token;
        this.team = team;
        this.expireDate = LocalDateTime.now().plusHours(EXPIRATION_HOURS);
    }

    private static String createToken() {
        return UUID.randomUUID().toString();
    }

    // 새로운 토큰 발급
    static TeamJoinToken createToken(Team team) {
        return new TeamJoinToken(createToken(), team);
    }

    // 연관 팀에 가입
    public void joinTeam(Member member) {
        validate();
        getTeam().join(member);
    }

    public Team getTeamInfo() {
        validate();
        return getTeam();
    }

    private void validate() {
        if (getExpireDate().isBefore(LocalDateTime.now())) {
            throw new TeamJoinTokenNotValidException();
        }
    }
}
