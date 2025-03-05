package com.feedhanjum.back_end.feedback.service;

import com.feedhanjum.back_end.core.event.EventPublisher;
import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
import com.feedhanjum.back_end.feedback.event.FeedbackReportCreatedEvent;
import com.feedhanjum.back_end.feedback.repository.FeedbackQueryRepository;
import com.feedhanjum.back_end.feedback.repository.FeedbackRepository;
import com.feedhanjum.back_end.feedback.repository.FrequentFeedbackRequestRepository;
import com.feedhanjum.back_end.feedback.repository.RegularFeedbackRequestRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.event.FrequentFeedbackRequestedEvent;
import com.feedhanjum.back_end.team.exception.TeamMembershipNotFoundException;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class FeedbackService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final ScheduleRepository scheduleRepository;
    private final FeedbackRepository feedbackRepository;
    private final RegularFeedbackRequestRepository regularFeedbackRequestRepository;
    private final EventPublisher eventPublisher;
    private final FeedbackQueryRepository feedbackQueryRepository;
    private final FrequentFeedbackRequestRepository frequentFeedbackRequestRepository;

    /**
     * @throws EntityNotFoundException sender id, receiver id, team id에 해당하는 엔티티가 없을 경우, receiver나 sender가 team에 속해있지 않을 경우
     */
    @Transactional
    public void requestFrequentFeedback(Long senderId, Long teamId, Long receiverId, String requestedContent) {
        Member sender = memberRepository.findById(senderId).orElseThrow(() -> new EntityNotFoundException("sender id에 해당하는 member가 없습니다."));
        Member receiver = memberRepository.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("receiver id에 해당하는 member가 없습니다."));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("team name에 해당하는 team이 없습니다."));

        team.requestFeedback(sender, receiver, requestedContent);

        eventPublisher.publishEvent(new FrequentFeedbackRequestedEvent(senderId, teamId, receiverId));
    }


    @Transactional
    public void skipRegularFeedback(Long scheduleId, Long memberId) {
        regularFeedbackRequestRepository.deleteAllByScheduleIdAndReceiverId(scheduleId, memberId);
    }

    /**
     * 해당 팀에서 receiver에게 온 모든 수시 피드백 요청을 거절한다.
     * 수시 피드백 요청 배너닫기 클릭 시 사용
     *
     * @throws EntityNotFoundException 팀에 속한 receiver가 없을 경우
     */
    @Transactional
    public void rejectAllFrequentFeedbackRequests(Long receiverId, Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("팀이 존재하지 않습니다"));
        Member receiver = memberRepository.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("멤버가 존재하지 않습니다"));

        team.rejectFeedbackRequests(receiver);
    }

    /**
     * 작성된 수시 피드백과 관련된 수시 피드백 요청을 삭제한다
     * 수시 피드백 작성 시 이벤트를 통해 호출
     *
     * @throws EntityNotFoundException         feedback id에 해당하는 feedback이 없을 경우
     * @throws TeamMembershipNotFoundException sender 가 team 에 속해있지 않을 경우
     */
    @Transactional
    public void deleteRelatedFrequentFeedbackRequest(FeedbackId feedbackId) {
        Feedback feedback = feedbackRepository
                .findById(feedbackId).orElseThrow(() -> new EntityNotFoundException("feedback id에 해당하는 feedback이 없습니다."));

        Member feedbackReceiver = memberRepository.findById(feedback.getReceiver().getId())
                .orElseThrow();
        Member feedbackSender = memberRepository.findById(feedback.getSender().getId())
                .orElseThrow();
        Team team = teamRepository.findById(feedback.getTeam().getId())
                .orElseThrow();

        team.removeFeedbackRequest(feedbackReceiver, feedbackSender);

    }

    @Transactional(readOnly = true)
    public Long getReceivedFeedbackCount(Long id) {
        return feedbackQueryRepository.findReceivedFeedbackCount(id);
    }

    @Transactional(readOnly = true)
    public Long getSentFeedbackCount(Long id) {
        return feedbackQueryRepository.findSentFeedbackCount(id);
    }


    @Transactional(readOnly = true)
    public void createFeedbackReports(Map<Long, Long> feedbackCreatedCounter) {
        for (Map.Entry<Long, Long> entry : feedbackCreatedCounter.entrySet()) {
            Long key = entry.getKey();
            Long value = entry.getValue();
            Long count = feedbackQueryRepository.findReceivedFeedbackCount(key);
            if (count >= 10 && count - value < 10) {
                eventPublisher.publishEvent(new FeedbackReportCreatedEvent(key));
            }
        }
    }

    @Transactional
    public void removeFeedbackRequest(Long memberId, Long teamId) {
        regularFeedbackRequestRepository.deleteAllByRequesterIdAndTeamId(memberId, teamId);
        frequentFeedbackRequestRepository.deleteAllBySenderIdAndTeamId(memberId, teamId);
        regularFeedbackRequestRepository.deleteAllByReceiverIdAndTeamId(memberId, teamId);
        frequentFeedbackRequestRepository.deleteAllByReceiverIdAndTeamId(memberId, teamId);
    }
}
