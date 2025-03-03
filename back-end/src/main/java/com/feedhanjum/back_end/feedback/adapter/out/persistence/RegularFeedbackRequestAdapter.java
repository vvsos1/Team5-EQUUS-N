package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import com.feedhanjum.back_end.feedback.application.port.out.request.regular.DeleteRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.SaveRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RegularFeedbackRequestAdapter implements LoadRegularFeedbackRequestPort, DeleteRegularFeedbackRequestPort, SaveRegularFeedbackRequestPort {
    private final RegularFeedbackRequestJpaEntityRepository regularFeedbackRequestJpaEntityRepository;
    private final RegularFeedbackRequestMapper regularFeedbackRequestMapper;

    @Override
    public void deleteRegularFeedbackRequest(Long regularFeedbackRequestId) {
        regularFeedbackRequestJpaEntityRepository.deleteById(regularFeedbackRequestId);
    }

    @Override
    public Optional<RegularFeedbackRequest> loadRegularFeedbackRequest(Long scheduleId, Long receiverId) {
        return regularFeedbackRequestJpaEntityRepository
                .findByScheduleIdAndReceiverId(scheduleId, receiverId)
                .map(regularFeedbackRequestMapper::toDomain);
    }

    @Override
    public void saveRegularFeedbackRequest(RegularFeedbackRequest domain) {
        var entity = regularFeedbackRequestMapper.fromDomain(domain);
        regularFeedbackRequestJpaEntityRepository.save(entity);
        regularFeedbackRequestMapper.setId(domain, entity.getId());
    }

}
