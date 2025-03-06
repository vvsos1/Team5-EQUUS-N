package com.feedhanjum.back_end.feedback.adapter.out.persistence.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

interface FeedbackJpaEntityRepository extends JpaRepository<FeedbackJpaEntity, Long> {
}
