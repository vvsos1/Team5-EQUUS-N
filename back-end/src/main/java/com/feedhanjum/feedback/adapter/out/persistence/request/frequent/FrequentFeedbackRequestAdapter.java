package com.feedhanjum.feedback.adapter.out.persistence.request.frequent;

import com.feedhanjum.feedback.application.port.out.request.frequent.DeleteFrequentFeedbackRequestPort;
import com.feedhanjum.feedback.application.port.out.request.frequent.LoadFrequentFeedbackRequestPort;
import com.feedhanjum.feedback.application.port.out.request.frequent.SaveFrequentFeedbackRequestPort;
import com.feedhanjum.feedback.domain.FrequentFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
class FrequentFeedbackRequestAdapter implements LoadFrequentFeedbackRequestPort, DeleteFrequentFeedbackRequestPort, SaveFrequentFeedbackRequestPort {
    private final FrequentFeedbackRequestJpaEntityRepository repository;
    private final FrequentFeedbackRequestMapper mapper;

    @Override
    public void save(FrequentFeedbackRequest request) {
        var entity = mapper.fromDomain(request);
        repository.save(entity);
    }

    @Override
    public void deleteByIds(Collection<Long> frequentFeedbackRequestIds) {
        repository.deleteAllById(frequentFeedbackRequestIds);
    }

    @Override
    public void deleteByTeamIdAndReceiverId(Long teamId, Long receiverId) {
        repository.deleteByTeam_IdAndReceiver_Id(teamId, receiverId);
    }

    @Override
    public void deleteByTeamIdAndRequesterId(Long teamId, Long requesterId) {
        repository.deleteByTeam_IdAndRequester_Id(teamId, requesterId);
    }

    @Override
    public Optional<FrequentFeedbackRequest> load(Long requesterId, Long teamId, Long receiverId) {
        return repository
                .findByRequester_IdAndTeam_IdAndReceiver_Id(requesterId, teamId, receiverId)
                .map(mapper::toDomain);
    }

    @Override
    public List<FrequentFeedbackRequest> load(Long teamId, Long receiverId) {
        return repository.findByTeam_IdAndReceiver_Id(teamId, receiverId);
    }
}