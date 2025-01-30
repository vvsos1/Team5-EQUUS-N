package com.feedhanjum.back_end.feedback.service;

import com.feedhanjum.back_end.event.EventPublisher;
import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackCategory;
import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.feedback.event.FrequentFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.repository.FeedbackRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final FeedbackRepository feedbackRepository;
    private final EventPublisher eventPublisher;

    public FeedbackService(MemberRepository memberRepository, TeamRepository teamRepository, FeedbackRepository feedbackRepository, EventPublisher eventPublisher) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
        this.feedbackRepository = feedbackRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * @throws EntityNotFoundException  sender id, receiver id, team id에 해당하는 엔티티가 없을 경우
     * @throws IllegalArgumentException 객관식 피드백이 보기에 없거나, 피드백 카테고리에 맞지 않는 값이 있을 경우, 또는 객관식 피드백이 1개 이상 5개 이하가 아닐 경우
     */
    @Transactional
    public Feedback sendFrequentFeedback(Long senderId, Long receiverId, Long teamId, FeedbackType feedbackType, FeedbackCategory feedbackCategory, List<String> objectiveFeedbacks, String subjectiveFeedback) {
        Member sender = memberRepository.findById(senderId).orElseThrow(() -> new EntityNotFoundException("sender id에 해당하는 member가 없습니다."));
        Member receiver = memberRepository.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("receiver id에 해당하는 member가 없습니다."));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("team id에 해당하는 team이 없습니다."));

        Feedback feedback = Feedback.builder()
                .sender(sender)
                .receiver(receiver)
                .team(team)
                .feedbackType(feedbackType)
                .feedbackCategory(feedbackCategory)
                .objectiveFeedbacks(objectiveFeedbacks)
                .subjectiveFeedback(subjectiveFeedback)
                .build();
        feedbackRepository.save(feedback);
        eventPublisher.publishEvent(new FrequentFeedbackCreatedEvent(feedback.getId()));
        return feedback;

    }
}
