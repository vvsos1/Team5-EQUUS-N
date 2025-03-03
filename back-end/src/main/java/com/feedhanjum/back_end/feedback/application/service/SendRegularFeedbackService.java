package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.core.event.Events;
import com.feedhanjum.back_end.feedback.application.port.in.SendRegularFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.command.SendRegularFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.out.LoadReceiverPort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadSenderPort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadTeamFromSchedulePort;
import com.feedhanjum.back_end.feedback.application.port.out.ParticipationValidatePort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.DeleteRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.domain.*;
import com.feedhanjum.back_end.feedback.event.RegularFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.exception.ParticipationNotFound;
import com.feedhanjum.back_end.feedback.exception.RegularFeedbackRequestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SendRegularFeedbackService implements SendRegularFeedbackUseCase {
    private final LoadRegularFeedbackRequestPort loadRegularFeedbackRequestPort;
    private final FeedbackIdGenerator feedbackIdGenerator;
    private final ParticipationValidatePort participationValidatePort;
    private final LoadTeamFromSchedulePort loadTeamFromSchedulePortPort;
    private final LoadSenderPort loadSenderPort;
    private final LoadReceiverPort loadReceiverPort;
    private final Clock clock;
    private final SaveFeedbackPort saveFeedbackPort;
    private final DeleteRegularFeedbackRequestPort deleteRegularFeedbackRequestPort;

    @Transactional
    @Override
    public void sendRegularFeedback(SendRegularFeedbackCommand command) {
        Long senderId = command.getSenderId();
        Long receiverId = command.getReceiverId();
        Long scheduleId = command.getScheduleId();

        Optional<RegularFeedbackRequest> regularFeedbackRequest = loadRegularFeedbackRequestPort.loadRegularFeedbackRequest(receiverId, scheduleId, senderId);
        if (regularFeedbackRequest.isEmpty())
            throw new RegularFeedbackRequestNotFoundException(scheduleId, senderId);

        validateParticipation(scheduleId, senderId);
        validateParticipation(scheduleId, receiverId);

        AssociatedTeam team = loadTeamFromSchedulePortPort.loadTeamFromSchedule(scheduleId);
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
        deleteRegularFeedbackRequestPort.deleteRegularFeedbackRequest(regularFeedbackRequest.get().getId());
        Events.raise(new RegularFeedbackCreatedEvent(feedbackId, senderId, receiverId));
    }

    private void validateParticipation(Long scheduleId, Long memberId) {
        if (!participationValidatePort.hasParticipation(scheduleId, memberId))
            throw new ParticipationNotFound(scheduleId, memberId);
    }

}
