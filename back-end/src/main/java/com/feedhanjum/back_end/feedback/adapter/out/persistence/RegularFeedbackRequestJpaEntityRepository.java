package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegularFeedbackRequestJpaEntityRepository extends JpaRepository<RegularFeedbackRequestJpaEntity, Long> {
    Optional<RegularFeedbackRequestJpaEntity> findByScheduleIdAndReceiverId(Long scheduleId, Long receiverId);
}
