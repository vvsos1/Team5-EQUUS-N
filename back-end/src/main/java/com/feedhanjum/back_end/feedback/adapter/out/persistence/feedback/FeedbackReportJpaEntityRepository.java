package com.feedhanjum.back_end.feedback.adapter.out.persistence.feedback;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface FeedbackReportJpaEntityRepository extends Repository<FeedbackReportJpaEntity, Long> {

    Optional<FeedbackReportJpaEntity> findByMemberId(Long memberId);

    void save(FeedbackReportJpaEntity entity);
}
