package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.core.event.Events;
import com.feedhanjum.back_end.feedback.application.port.in.SendFrequentFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.SendFrequentFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.application.port.out.team.LoadTeamPort;
import com.feedhanjum.back_end.feedback.application.port.out.team.MembershipValidatePort;
import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackIdGenerator;
import com.feedhanjum.back_end.feedback.event.FrequentFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.exception.MembershipNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
class SendFrequentFeedbackService implements SendFrequentFeedbackUseCase {
    private final FeedbackIdGenerator feedbackIdGenerator;
    private final MembershipValidatePort membershipValidatePort;
    private final LoadTeamPort loadTeamPort;
    private final LoadMemberPort loadMemberPort;
    private final Clock clock;
    private final SaveFeedbackPort saveFeedbackPort;

    @Transactional
    @Override
    public void sendFrequentFeedback(SendFrequentFeedbackCommand command) {
        Long senderId = command.getSenderId();
        Long receiverId = command.getReceiverId();
        Long teamId = command.getTeamId();

        validateMembership(teamId, senderId);
        validateMembership(teamId, receiverId);

        AssociatedTeam team = loadTeamPort.loadTeam(teamId).orElseThrow();
        FeedbackMember member = loadMemberPort.loadMember(senderId).orElseThrow();
        FeedbackMember receiver = loadMemberPort.loadMember(receiverId).orElseThrow();

        FeedbackId feedbackId = feedbackIdGenerator.generateFeedbackId();

        Feedback feedback = new Feedback(
                feedbackId,
                command.getFeedbackType(),
                command.getFeedbackFeeling(),
                command.getObjectiveFeedbacks(),
                command.getSubjectiveFeedback(),
                false,
                member,
                receiver,
                team,
                LocalDateTime.now(clock)
        );

        saveFeedbackPort.saveFeedback(feedback);
        Events.raise(new FrequentFeedbackCreatedEvent(feedbackId, senderId, receiverId));
    }

    private void validateMembership(Long teamId, Long memberId) {
        if (!membershipValidatePort.hasMembership(teamId, memberId))
            throw new MembershipNotFound(teamId, memberId);
    }
}
