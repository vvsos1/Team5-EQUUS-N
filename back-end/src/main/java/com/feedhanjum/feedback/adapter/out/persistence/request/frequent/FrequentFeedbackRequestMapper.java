package com.feedhanjum.feedback.adapter.out.persistence.request.frequent;

import com.feedhanjum.feedback.domain.FrequentFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@RequiredArgsConstructor
@Component
class FrequentFeedbackRequestMapper {

    public FrequentFeedbackRequestJpaEntity fromDomain(FrequentFeedbackRequest domain) {
        return new FrequentFeedbackRequestJpaEntity(
                domain.getRequestedContent(),
                domain.getRequester(),
                domain.getTeam(),
                domain.getReceiver(),
                domain.getCreatedAt()
        );
    }

    public FrequentFeedbackRequest toDomain(FrequentFeedbackRequestJpaEntity entity) {
        var domain = new FrequentFeedbackRequest(entity.getRequestedContent(), entity.getRequester(), entity.getTeam(), entity.getReceiver(), entity.getCreatedAt());
        setId(domain, entity.getId());
        return domain;
    }

    public void setId(FrequentFeedbackRequest domain, Long id) {
        try {
            Field idField = domain.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(domain, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set ID using reflection", e);
        }
    }
}
