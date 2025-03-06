package com.feedhanjum.back_end.feedback.adapter.out.persistence.feedback;

import com.feedhanjum.back_end.feedback.application.port.out.feedback.FeedbackReportLock;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.FeedbackReportLockManager;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadFeedbackReportPort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackReportPort;
import com.feedhanjum.back_end.feedback.domain.FeedbackReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class FeedbackReportPersistenceAdapter implements FeedbackReportLockManager, LoadFeedbackReportPort, SaveFeedbackReportPort {
    private final EntityManager entityManager;
    private final FeedbackReportJpaEntityRepository feedbackReportJpaEntityRepository;
    private final FeedbackReportMapper feedbackReportMapper;

    @Override
    public Optional<FeedbackReportLock> lock(Long memberId) {
        return feedbackReportJpaEntityRepository.findByMemberId(memberId)
                .map(report -> {
                    entityManager.lock(report, LockModeType.PESSIMISTIC_WRITE);
                    return new FeedbackReportJpaLock(report);
                });
    }

    @Override
    public void unlock(FeedbackReportLock lock) {
        // transaction 종료 시 락이 자동으로 풀리므로 아무것도 하지 않음
    }

    @Override
    public Optional<FeedbackReport> load(Long memberId) {
        return feedbackReportJpaEntityRepository.findByMemberId(memberId).map(entity -> {
            entityManager.lock(entity, LockModeType.PESSIMISTIC_READ);
            return feedbackReportMapper.toDomain(entity);
        });
    }

    @Override
    public void save(FeedbackReport feedbackReport) {
        var entity = feedbackReportMapper.fromDomain(feedbackReport);
        feedbackReportJpaEntityRepository.save(entity);
    }
}
