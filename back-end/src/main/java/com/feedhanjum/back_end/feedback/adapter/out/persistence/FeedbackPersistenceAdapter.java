package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadFeedbackPort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
class FeedbackPersistenceAdapter implements SaveFeedbackPort, LoadFeedbackPort {
    private final FeedbackJpaEntityRepository feedbackJpaEntityRepository;

    @Override
    public void saveFeedback(Feedback feedback) {
        var entity = FeedbackJpaEntity.fromDomain(feedback);
        feedbackJpaEntityRepository.save(entity);
    }

    @Override
    public Optional<Feedback> loadFeedback(FeedbackId feedbackId) {
        return feedbackJpaEntityRepository
                .findById(feedbackId.getId())
                .map(FeedbackJpaEntity::toDomain);
    }

    @Override
    public List<Feedback> loadFeedbacks() {
        return feedbackJpaEntityRepository
                .findAll()
                .stream()
                .map(FeedbackJpaEntity::toDomain)
                .toList();
    }
}
