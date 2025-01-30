package com.feedhanjum.back_end.feedback.service;

import com.feedhanjum.back_end.event.EventPublisher;
import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackCategory;
import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.feedback.event.FrequentFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.repository.FeedbackRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.team.domain.FrequentFeedbackRequest;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.domain.TeamMember;
import com.feedhanjum.back_end.team.event.FrequentFeedbackRequestCreatedEvent;
import com.feedhanjum.back_end.team.repository.FrequentFeedbackRequestRepository;
import com.feedhanjum.back_end.team.repository.TeamMemberRepository;
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
    private final TeamMemberRepository teamMemberRepository;
    private final FrequentFeedbackRequestRepository frequentFeedbackRequestRepository;
    private final EventPublisher eventPublisher;

    public FeedbackService(MemberRepository memberRepository, TeamRepository teamRepository, FeedbackRepository feedbackRepository, TeamMemberRepository teamMemberRepository, FrequentFeedbackRequestRepository frequentFeedbackRequestRepository, EventPublisher eventPublisher) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
        this.feedbackRepository = feedbackRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.frequentFeedbackRequestRepository = frequentFeedbackRequestRepository;
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

    /**
     * @throws EntityNotFoundException sender id, receiver id, team name에 해당하는 엔티티가 없을 경우, receiver나 sender가 team에 속해있지 않을 경우
     */
    @Transactional
    public FrequentFeedbackRequest requestFrequentFeedback(Long senderId, String teamName, Long receiverId, String requestedContent) {
        Member sender = memberRepository.findById(senderId).orElseThrow(() -> new EntityNotFoundException("sender id에 해당하는 member가 없습니다."));
        memberRepository.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("receiver id에 해당하는 member가 없습니다."));
        Team team = teamRepository.findByName(teamName).orElseThrow(() -> new EntityNotFoundException("team name에 해당하는 team이 없습니다."));

        teamMemberRepository.findByMemberIdAndTeamId(senderId, team.getId()).orElseThrow(() -> new EntityNotFoundException("sender 가 team 에 속해있지 않습니다"));
        TeamMember teamMember = teamMemberRepository.findByMemberIdAndTeamId(receiverId, team.getId()).orElseThrow(() -> new EntityNotFoundException("receiver 가 team 에 속해있지 않습니다"));

        FrequentFeedbackRequest frequentFeedbackRequest = new FrequentFeedbackRequest(requestedContent, teamMember, sender);

        frequentFeedbackRequestRepository.save(frequentFeedbackRequest);
        eventPublisher.publishEvent(new FrequentFeedbackRequestCreatedEvent(frequentFeedbackRequest.getId()));
        return frequentFeedbackRequest;
    }

    /**
     * @throws EntityNotFoundException receiver id, team name에 해당하는 엔티티가 없을 경우, receiver가 team에 속해있지 않을 경우
     */
    @Transactional(readOnly = true)
    public List<FrequentFeedbackRequest> getFrequentFeedbackRequests(Long receiverId, String teamName) {
        memberRepository.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("receiver id에 해당하는 member가 없습니다."));
        Team team = teamRepository.findByName(teamName).orElseThrow(() -> new EntityNotFoundException("team name에 해당하는 team이 없습니다."));
        TeamMember teamMember = teamMemberRepository.findByMemberIdAndTeamId(receiverId, team.getId()).orElseThrow(() -> new EntityNotFoundException("receiver 가 team 에 속해있지 않습니다"));

        return frequentFeedbackRequestRepository.findByTeamMember(teamMember);
    }
}
