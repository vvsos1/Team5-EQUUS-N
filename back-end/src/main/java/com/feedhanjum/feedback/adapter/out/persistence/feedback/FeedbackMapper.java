package com.feedhanjum.feedback.adapter.out.persistence.feedback;

import com.feedhanjum.feedback.domain.feedback.*;
import com.feedhanjum.feedback.dto.ReceivedFeedbackDto;
import com.feedhanjum.feedback.dto.SentFeedbackDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class FeedbackMapper {

    public FeedbackJpaEntity fromDomain(Feedback feedback) {
        return new FeedbackJpaEntity(
                feedback.getId().getId(),
                feedback.getFeedbackType().name(),
                feedback.getFeedbackFeeling().name(),
                feedback.getObjectiveFeedbacks().stream().map(ObjectiveFeedback::name).collect(Collectors.toSet()),
                feedback.getSubjectiveFeedback(),
                feedback.isLiked(),
                feedback.getSender(),
                feedback.getReceiver(),
                feedback.getTeam(),
                feedback.getCreatedAt()
        );
    }

    public Feedback toDomain(FeedbackJpaEntity entity) {
        return new Feedback(
                new FeedbackId(entity.getId()),
                FeedbackType.valueOf(entity.getFeedbackType()),
                FeedbackFeeling.valueOf(entity.getFeedbackFeeling()),
                entity.getObjectiveFeedbacks().stream().map(ObjectiveFeedback::valueOf).toList(),
                entity.getSubjectiveFeedback(),
                entity.isLiked(),
                entity.getSender(),
                entity.getReceiver(),
                entity.getTeam(),
                entity.getCreatedAt()
        );
    }

    public SentFeedbackDto toSentFeedbackDto(FeedbackJpaEntity entity) {
        return new SentFeedbackDto(toDomain(entity));
    }

    public ReceivedFeedbackDto toReceivedFeedbackDto(FeedbackJpaEntity entity) {
        return new ReceivedFeedbackDto(toDomain(entity));
    }
}
