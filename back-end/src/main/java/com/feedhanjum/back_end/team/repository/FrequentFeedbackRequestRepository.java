package com.feedhanjum.back_end.team.repository;

import com.feedhanjum.back_end.team.domain.FrequentFeedbackRequest;
import com.feedhanjum.back_end.team.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FrequentFeedbackRequestRepository extends JpaRepository<FrequentFeedbackRequest, Long> {
    List<FrequentFeedbackRequest> findByTeamMember(TeamMember teamMember);

    void deleteAllByTeamMember(TeamMember teamMember);
}