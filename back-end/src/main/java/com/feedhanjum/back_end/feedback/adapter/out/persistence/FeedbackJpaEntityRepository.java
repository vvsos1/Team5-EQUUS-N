package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackJpaEntityRepository extends JpaRepository<FeedbackJpaEntity, Long> {
    List<FeedbackJpaEntity> findAllByReceiverId(Long receiverId);
}
