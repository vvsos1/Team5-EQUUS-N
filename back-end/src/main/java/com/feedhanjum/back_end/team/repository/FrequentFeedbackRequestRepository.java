package com.feedhanjum.back_end.team.repository;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.team.domain.FrequentFeedbackRequest;
import com.feedhanjum.back_end.team.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FrequentFeedbackRequestRepository extends JpaRepository<FrequentFeedbackRequest, Long> {
    Optional<FrequentFeedbackRequest> findByTeamMemberAndRequester(TeamMember teamMember, Member requester);

    void deleteAllByTeamMember(TeamMember teamMember);
}