package com.feedhanjum.back_end.feedback.adapter.out.persistence.request.regular;

import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@RequiredArgsConstructor
@Component
class RegularFeedbackRequestMapper {

    public RegularFeedbackRequestJpaEntity fromDomain(RegularFeedbackRequest domain) {
        return new RegularFeedbackRequestJpaEntity(
                domain.getRequester(),
                domain.getSchedule(),
                domain.getReceiver(),
                domain.getCreatedAt()
        );
    }

    public RegularFeedbackRequest toDomain(RegularFeedbackRequestJpaEntity entity) {
        var domain = new RegularFeedbackRequest(entity.getCreatedAt(), entity.getRequester(), entity.getSchedule(), entity.getReceiver());
        setId(domain, entity.getId());
        return domain;
    }

    public void setId(RegularFeedbackRequest domain, Long id) {
        try {
            Field idField = domain.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(domain, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set ID using reflection", e);
        }
    }
}
