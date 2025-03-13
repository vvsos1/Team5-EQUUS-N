package com.feedhanjum.feedback.adapter.out.persistence.request.regular;

import com.feedhanjum.feedback.application.port.out.request.regular.DeleteRegularFeedbackRequestPort;
import com.feedhanjum.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestPort;
import com.feedhanjum.feedback.application.port.out.request.regular.SaveRegularFeedbackRequestPort;
import com.feedhanjum.feedback.domain.RegularFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
class RegularFeedbackRequestAdapter implements LoadRegularFeedbackRequestPort, DeleteRegularFeedbackRequestPort, SaveRegularFeedbackRequestPort {
    private final RegularFeedbackRequestJpaEntityRepository repository;
    private final RegularFeedbackRequestMapper mapper;

    @Override
    public void deleteByIds(Collection<Long> regularFeedbackRequestIds) {
        repository.deleteAllById(regularFeedbackRequestIds);
    }

    @Override
    public void deleteByTeamIdAndReceiverId(Long teamId, Long receiverId) {
        repository.deleteBySchedule_TeamIdAndReceiver_Id(teamId, receiverId);
    }

    @Override
    public void deleteByTeamIdAndRequesterId(Long teamId, Long requesterId) {
        repository.deleteBySchedule_TeamIdAndRequester_Id(teamId, requesterId);
    }


    @Override
    public Optional<RegularFeedbackRequest> load(Long requesterId, Long scheduleId, Long receiverId) {
        return repository
                .findByRequester_IdAndSchedule_IdAndReceiver_Id(requesterId, scheduleId, receiverId)
                .map(mapper::toDomain);
    }

    @Override
    public void save(RegularFeedbackRequest domain) {
        var entity = mapper.fromDomain(domain);
        repository.save(entity);
        mapper.setId(domain, entity.getId());
    }

    @Override
    public List<RegularFeedbackRequest> load(Long scheduleId, Long receiverId) {
        var entities = repository.findAllBySchedule_IdAndReceiver_Id(scheduleId, receiverId);
        return entities.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Long countByScheduleIdAndReceiverId(Long scheduleId, Long receiverId) {
        return repository.countBySchedule_IdAndReceiver_Id(scheduleId, receiverId);
    }
}
