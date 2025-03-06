package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import com.feedhanjum.back_end.feedback.application.port.out.request.frequent.DeleteFrequentFeedbackRequestPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@RequiredArgsConstructor
@Component
public class FrequentFeedbackRequestAdapter implements DeleteFrequentFeedbackRequestPort {
    @Override
    public void deleteByIds(Collection<Long> frequentFeedbackRequestIds) {

    }

    @Override
    public void deleteByTeamIdAndReceiverId(Long teamId, Long receiverId) {

    }

    @Override
    public void deleteByTeamIdAndRequesterId(Long teamId, Long requesterId) {

    }
}
