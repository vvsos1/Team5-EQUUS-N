package com.feedhanjum.back_end.feedback.adapter.out.persistence.request.frequent;

import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface FrequentFeedbackRequestJpaEntityRepository extends JpaRepository<FrequentFeedbackRequestJpaEntity, Long> {

    Optional<FrequentFeedbackRequestJpaEntity> findByRequester_IdAndTeam_IdAndReceiver_Id(Long requesterId, Long teamId, Long receiverId);

    void deleteByTeam_IdAndReceiver_Id(Long teamId, Long receiverId);

    void deleteByTeam_IdAndRequester_Id(Long teamId, Long requesterId);

    List<FrequentFeedbackRequest> findByTeam_IdAndReceiver_Id(Long teamId, Long receiverId);
}
