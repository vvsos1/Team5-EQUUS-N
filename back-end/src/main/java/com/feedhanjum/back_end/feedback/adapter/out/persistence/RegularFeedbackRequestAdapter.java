package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import com.feedhanjum.back_end.feedback.application.port.out.request.regular.DeleteRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.SaveRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
class RegularFeedbackRequestAdapter implements LoadRegularFeedbackRequestPort, DeleteRegularFeedbackRequestPort, SaveRegularFeedbackRequestPort {
    private final RegularFeedbackRequestJpaEntityRepository regularFeedbackRequestJpaEntityRepository;
    private final RegularFeedbackRequestMapper regularFeedbackRequestMapper;

    @Override
    public void deleteByIds(Collection<Long> regularFeedbackRequestIds) {
        regularFeedbackRequestJpaEntityRepository.deleteAllById(regularFeedbackRequestIds);
    }

    @Override
    public void deleteByTeamIdAndReceiverId(Long teamId, Long receiverId) {
        regularFeedbackRequestJpaEntityRepository.deleteBySchedule_TeamIdAndReceiver_Id(teamId, receiverId);
    }

    @Override
    public void deleteByTeamIdAndRequesterId(Long teamId, Long requesterId) {
        regularFeedbackRequestJpaEntityRepository.deleteBySchedule_TeamIdAndRequester_Id(teamId, requesterId);
    }


    @Override
    public Optional<RegularFeedbackRequest> load(Long requesterId, Long scheduleId, Long receiverId) {
        return regularFeedbackRequestJpaEntityRepository
                .findByRequesterIdAndScheduleIdAndReceiverId(requesterId, scheduleId, receiverId)
                .map(regularFeedbackRequestMapper::toDomain);
    }

    @Override
    public void save(RegularFeedbackRequest domain) {
        var entity = regularFeedbackRequestMapper.fromDomain(domain);
        regularFeedbackRequestJpaEntityRepository.save(entity);
        regularFeedbackRequestMapper.setId(domain, entity.getId());
    }

    @Override
    public List<RegularFeedbackRequest> load(Long scheduleId, Long receiverId) {
        var entities = regularFeedbackRequestJpaEntityRepository.findAllByScheduleIdAndReceiverId(scheduleId, receiverId);
        return entities.stream().map(regularFeedbackRequestMapper::toDomain).toList();
    }

    @Override
    public Long countByScheduleIdAndReceiverId(Long scheduleId, Long receiverId) {
        return regularFeedbackRequestJpaEntityRepository.countBySchedule_IdAndReceiver_Id(scheduleId, receiverId);
    }
}
