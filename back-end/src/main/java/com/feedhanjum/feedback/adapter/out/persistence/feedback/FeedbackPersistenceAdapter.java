package com.feedhanjum.feedback.adapter.out.persistence.feedback;

import com.feedhanjum.feedback.application.port.out.feedback.CountFeedbackPort;
import com.feedhanjum.feedback.application.port.out.feedback.LoadFeedbackPort;
import com.feedhanjum.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
class FeedbackPersistenceAdapter implements SaveFeedbackPort, LoadFeedbackPort, CountFeedbackPort {
    private final FeedbackJpaEntityRepository feedbackJpaEntityRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public void saveFeedback(Feedback feedback) {
        var entity = feedbackMapper.fromDomain(feedback);
        feedbackJpaEntityRepository.save(entity);
    }

    @Override
    public Optional<Feedback> loadFeedback(FeedbackId feedbackId) {
        return feedbackJpaEntityRepository
                .findById(feedbackId.getId())
                .map(feedbackMapper::toDomain);
    }

    @Override
    public List<Feedback> loadFeedbacks() {
        return feedbackJpaEntityRepository
                .findAll()
                .stream()
                .map(feedbackMapper::toDomain)
                .toList();
    }

    @Override
    public Long countReceivedFeedback(Long receiverId) {
        return feedbackJpaEntityRepository.countByReceiver_Id(receiverId);
    }

    @Override
    public Long countSentFeedback(Long senderId) {
        return feedbackJpaEntityRepository.countBySender_Id(senderId);
    }
}
