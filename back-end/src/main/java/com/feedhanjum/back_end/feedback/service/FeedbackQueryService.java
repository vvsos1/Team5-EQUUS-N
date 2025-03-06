package com.feedhanjum.back_end.feedback.service;


import com.feedhanjum.back_end.feedback.controller.dto.response.FrequentFeedbackRequestForApiResponse;
import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;
import com.feedhanjum.back_end.feedback.repository.FrequentFeedbackRequestQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class FeedbackQueryService {
    private final FrequentFeedbackRequestQueryRepository frequentFeedbackRequestQueryRepository;

    @Transactional(readOnly = true)
    public List<FrequentFeedbackRequestForApiResponse> getFrequentFeedbackRequests(Long receiverId, Long teamId) {
        List<FrequentFeedbackRequest> requests = frequentFeedbackRequestQueryRepository.getFrequentFeedbackRequests(receiverId, teamId);
        return requests.stream().map(FrequentFeedbackRequestForApiResponse::from).toList();
    }

}
