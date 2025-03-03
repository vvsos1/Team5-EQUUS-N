package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadFeedbackPort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadReceivedFeedbackPort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
class FeedbackPersistenceAdapter implements SaveFeedbackPort, LoadReceivedFeedbackPort, LoadFeedbackPort {
    private final FeedbackJpaEntityRepository feedbackJpaEntityRepository;

    @Override
    public void saveFeedback(Feedback feedback) {
        var entity = FeedbackJpaEntity.fromDomain(feedback);
        feedbackJpaEntityRepository.save(entity);
    }

    @Override
    public List<Feedback> loadReceivedFeedback(Long receiverId) {
        var entities = feedbackJpaEntityRepository.findAllByReceiverId(receiverId);
        return entities.stream().map(FeedbackJpaEntity::toDomain).toList();
    }

    @Override
    public Optional<Feedback> loadFeedback(FeedbackId feedbackId) {
        return feedbackJpaEntityRepository
                .findById(feedbackId.getId())
                .map(FeedbackJpaEntity::toDomain);
    }
}
