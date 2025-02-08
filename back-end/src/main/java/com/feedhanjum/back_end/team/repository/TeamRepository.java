package com.feedhanjum.back_end.team.repository;

import com.feedhanjum.back_end.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
