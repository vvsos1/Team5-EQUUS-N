package com.feedhanjum.feedback.application.service;

import com.feedhanjum.core.event.Events;
import com.feedhanjum.feedback.application.port.in.feedback.SendRegularFeedbackUseCase;
import com.feedhanjum.feedback.application.port.in.feedback.command.SendRegularFeedbackCommand;
import com.feedhanjum.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.feedback.application.port.out.request.regular.DeleteRegularFeedbackRequestPort;
import com.feedhanjum.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestPort;
import com.feedhanjum.feedback.application.port.out.schedule.ParticipationValidatePort;
import com.feedhanjum.feedback.application.port.out.team.LoadTeamFromSchedulePort;
import com.feedhanjum.feedback.domain.AssociatedTeam;
import com.feedhanjum.feedback.domain.FeedbackMember;
import com.feedhanjum.feedback.domain.RegularFeedbackRequest;
import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackId;
import com.feedhanjum.feedback.domain.feedback.FeedbackIdGenerator;
import com.feedhanjum.feedback.event.RegularFeedbackCreatedEvent;
import com.feedhanjum.feedback.exception.ParticipationNotFound;
import com.feedhanjum.feedback.exception.RegularFeedbackRequestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
class SendRegularFeedbackService implements SendRegularFeedbackUseCase {
    private final LoadRegularFeedbackRequestPort loadRegularFeedbackRequestPort;
    private final FeedbackIdGenerator feedbackIdGenerator;
    private final ParticipationValidatePort participationValidatePort;
    private final LoadTeamFromSchedulePort loadTeamFromSchedulePortPort;
    private final LoadMemberPort loadMemberPort;
    private final Clock clock;
    private final SaveFeedbackPort saveFeedbackPort;
    private final DeleteRegularFeedbackRequestPort deleteRegularFeedbackRequestPort;

    @Override
    @Transactional
    public void sendRegularFeedback(SendRegularFeedbackCommand command) {
        Long senderId = command.getSenderId();
        Long receiverId = command.getReceiverId();
        Long scheduleId = command.getScheduleId();

        Optional<RegularFeedbackRequest> regularFeedbackRequest = loadRegularFeedbackRequestPort.load(receiverId, scheduleId, senderId);
        if (regularFeedbackRequest.isEmpty())
            throw new RegularFeedbackRequestNotFoundException(scheduleId, senderId);

        validateParticipation(scheduleId, senderId);
        validateParticipation(scheduleId, receiverId);

        AssociatedTeam team = loadTeamFromSchedulePortPort.loadTeamFromSchedule(scheduleId).orElseThrow();
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
        deleteRegularFeedbackRequestPort.deleteById(regularFeedbackRequest.get().getId());
        Events.raise(new RegularFeedbackCreatedEvent(feedbackId, senderId, receiverId));
    }

    private void validateParticipation(Long scheduleId, Long memberId) {
        if (!participationValidatePort.hasParticipation(scheduleId, memberId))
            throw new ParticipationNotFound(scheduleId, memberId);
    }

}
