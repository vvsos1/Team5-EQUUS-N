package com.feedhanjum.back_end.feedback.service;


import com.feedhanjum.back_end.feedback.controller.dto.response.FrequentFeedbackRequestForApiResponse;
import com.feedhanjum.back_end.feedback.domain.FeedbackReport;
import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;
import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;
import com.feedhanjum.back_end.feedback.repository.FeedbackQueryRepository;
import com.feedhanjum.back_end.feedback.repository.FrequentFeedbackRequestQueryRepository;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class FeedbackQueryService {
    private final FeedbackQueryRepository feedbackQueryRepository;
    private final MemberRepository memberRepository;
    private final FrequentFeedbackRequestQueryRepository frequentFeedbackRequestQueryRepository;

    @Transactional(readOnly = true)
    public List<FrequentFeedbackRequestForApiResponse> getFrequentFeedbackRequests(Long receiverId, Long teamId) {
        List<FrequentFeedbackRequest> requests = frequentFeedbackRequestQueryRepository.getFrequentFeedbackRequests(receiverId, teamId);
        return requests.stream().map(FrequentFeedbackRequestForApiResponse::from).toList();
    }


    /**
     * @throws EntityNotFoundException when memberId does not exist
     */
    @Transactional(readOnly = true)
    public FeedbackReport getFeedbackReport(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("memberId에 해당하는 Member가 없습니다."));
        List<Feedback> receivedFeedback = feedbackQueryRepository.findReceivedFeedbacks(memberId);
        return FeedbackReport.fromFeedbacks(receivedFeedback);
    }
}
