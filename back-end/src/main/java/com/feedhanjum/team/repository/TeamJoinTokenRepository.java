package com.feedhanjum.team.repository;

import com.feedhanjum.team.domain.TeamJoinToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJoinTokenRepository extends JpaRepository<TeamJoinToken, String> {
}