package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.core.event.Events;
import com.feedhanjum.back_end.feedback.application.port.in.SendFrequentFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.in.SendFrequentFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.out.*;
import com.feedhanjum.back_end.feedback.domain.*;
import com.feedhanjum.back_end.feedback.event.FrequentFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.exception.MembershipNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SendFrequentFeedbackService implements SendFrequentFeedbackUseCase {
    private final FeedbackIdGenerator feedbackIdGenerator;
    private final MembershipValidatePort membershipValidatePort;
    private final LoadTeamPort loadTeamPort;
    private final LoadSenderPort loadSenderPort;
    private final LoadReceiverPort loadReceiverPort;
    private final Clock clock;
    private final SaveFeedbackPort saveFeedbackPort;

    @Override
    @Transactional
    public void sendFrequentFeedback(SendFrequentFeedbackCommand command) {
        Long senderId = command.getSenderId();
        Long receiverId = command.getReceiverId();
        Long teamId = command.getTeamId();

        validateMembership(teamId, senderId);
        validateMembership(teamId, receiverId);

        AssociatedTeam team = loadTeamPort.loadTeam(teamId);
        Sender sender = loadSenderPort.loadSender(senderId);
        Receiver receiver = loadReceiverPort.loadReceiver(receiverId);

        FeedbackId feedbackId = feedbackIdGenerator.generateFeedbackId();

        Feedback feedback = new Feedback(
                feedbackId,
                command.getFeedbackType(),
                command.getFeedbackFeeling(),
                command.getObjectiveFeedbacks(),
                command.getSubjectiveFeedback(),
                false,
                sender,
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
