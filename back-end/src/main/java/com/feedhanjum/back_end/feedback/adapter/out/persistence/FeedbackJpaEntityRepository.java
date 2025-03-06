package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface FeedbackJpaEntityRepository extends JpaRepository<FeedbackJpaEntity, Long> {
}
