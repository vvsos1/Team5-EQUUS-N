package com.feedhanjum.back_end.team.repository;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.team.domain.FrequentFeedbackRequest;
import com.feedhanjum.back_end.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FrequentFeedbackRequestRepository extends JpaRepository<FrequentFeedbackRequest, Long> {
    Optional<FrequentFeedbackRequest> findByReceiverAndSender(Member receiver, Member sender);

    void deleteAllByReceiverAndTeam(Member receiver, Team team);
}