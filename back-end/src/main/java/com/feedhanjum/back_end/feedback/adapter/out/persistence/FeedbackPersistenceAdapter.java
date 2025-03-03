package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import com.feedhanjum.back_end.feedback.application.port.out.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.domain.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class FeedbackPersistenceAdapter implements SaveFeedbackPort {
    private final FeedbackJpaEntityRepository feedbackJpaEntityRepository;

    @Override
    public void saveFeedback(Feedback feedback) {
        FeedbackJpaEntity entity = FeedbackJpaEntity.fromDomain(feedback);
        feedbackJpaEntityRepository.save(entity);
    }
}
