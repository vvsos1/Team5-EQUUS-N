package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface RegularFeedbackRequestJpaEntityRepository extends JpaRepository<RegularFeedbackRequestJpaEntity, Long> {

    Optional<RegularFeedbackRequestJpaEntity> findByRequesterIdAndScheduleIdAndReceiverId(Long requesterId, Long scheduleId, Long receiverId);

    List<RegularFeedbackRequestJpaEntity> findAllByScheduleIdAndReceiverId(Long scheduleId, Long receiverId);

    void deleteBySchedule_TeamIdAndReceiver_Id(Long teamId, Long receiverId);

    void deleteBySchedule_TeamIdAndRequester_Id(Long teamId, Long requesterId);

    Long countBySchedule_IdAndReceiver_Id(Long scheduleId, Long receiverId);
}
