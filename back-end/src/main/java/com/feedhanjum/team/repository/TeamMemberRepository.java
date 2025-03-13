package com.feedhanjum.team.repository;

import com.feedhanjum.team.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findByMemberIdAndTeamId(Long memberId, Long teamId);
}
