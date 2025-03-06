package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface RegularFeedbackRequestJpaEntityRepository extends JpaRepository<RegularFeedbackRequestJpaEntity, Long> {

    Optional<RegularFeedbackRequestJpaEntity> findByRequesterIdAndScheduleIdAndReceiverId(Long requesterId, Long scheduleId, Long receiverId);

    List<RegularFeedbackRequestJpaEntity> findAllByScheduleIdAndReceiverId(Long scheduleId, Long receiverId);
}
