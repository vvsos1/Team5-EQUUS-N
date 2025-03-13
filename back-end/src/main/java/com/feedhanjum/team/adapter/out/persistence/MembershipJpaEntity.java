package com.feedhanjum.team.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MembershipJpaEntity {

    @Id
    @Column(name = "team_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "member_id")
    private Long memberId;

    public MembershipJpaEntity(Long id, Long teamId, Long memberId) {
        this.id = id;
        this.teamId = teamId;
        this.memberId = memberId;
    }
}