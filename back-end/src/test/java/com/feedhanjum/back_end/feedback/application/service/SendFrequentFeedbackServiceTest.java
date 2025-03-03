package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.core.event.EventPublisher;
import com.feedhanjum.back_end.core.event.Events;
import com.feedhanjum.back_end.feedback.application.port.in.command.SendFrequentFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.out.LoadReceiverPort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadSenderPort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadTeamPort;
import com.feedhanjum.back_end.feedback.application.port.out.MembershipValidatePort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.domain.*;
import com.feedhanjum.back_end.feedback.event.FrequentFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.exception.MembershipNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.util.List;

import static com.feedhanjum.back_end.test.util.Fixture.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SendFrequentFeedbackServiceTest {
    @Spy
    FeedbackIdGenerator feedbackIdGenerator = defaultFeedbackIdGenerator();
    @Mock
    MembershipValidatePort membershipValidatePort;
    @Mock
    LoadTeamPort loadTeamPort;
    @Mock
    LoadSenderPort loadSenderPort;
    @Mock
    LoadReceiverPort loadReceiverPort;
    @Mock
    SaveFeedbackPort saveFeedbackPort;
    @Spy
    Clock clock = defaultClock();

    @InjectMocks
    SendFrequentFeedbackService sendFrequentFeedbackService;

    EventPublisher eventPublisher;

    @BeforeEach
    void setup() {
        eventPublisher = mock();
        Events.setPublisher(eventPublisher);
    }

    @Test
    @DisplayName("수시 피드백 전송 성공")
    void test1() {
        // given
        var sender = defaultSender();
        var receiver = defaultReceiver();
        var team = defaultTeam();

        var feedbackType = FeedbackType.IDENTIFIED;
        var feedbackFeeling = FeedbackFeeling.POSITIVE;
        var objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
        var subjectiveFeedback = "좋아요";

        var command = new SendFrequentFeedbackCommand(sender.getId(), receiver.getId(), team.getId(), feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);

        givenMembershipWillExists(team.getId(), sender.getId());
        givenMembershipWillExists(team.getId(), receiver.getId());

        givenTeamWillLoad(team);
        givenSenderWillLoad(sender);
        givenReceiverWillLoad(receiver);

        // when
        sendFrequentFeedbackService.sendFrequentFeedback(command);

        // then
        verify(saveFeedbackPort).saveFeedback(any(Feedback.class));
        verify(eventPublisher).publishEvent(any(FrequentFeedbackCreatedEvent.class));
    }

    @Test
    @DisplayName("수시 피드백 전송 실패 - sender membership이 없을 경우")
    void test2() {
        // given
        var sender = defaultSender();
        var receiver = defaultReceiver();
        var team = defaultTeam();

        var feedbackType = FeedbackType.IDENTIFIED;
        var feedbackFeeling = FeedbackFeeling.POSITIVE;
        var objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
        var subjectiveFeedback = "좋아요";

        var command = new SendFrequentFeedbackCommand(sender.getId(), receiver.getId(), team.getId(), feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);

        // when
        assertThatThrownBy(() -> sendFrequentFeedbackService.sendFrequentFeedback(command))
                .isInstanceOf(MembershipNotFound.class);

        // then
    }

    @Test
    @DisplayName("수시 피드백 전송 실패 - receiver membership이 없을 경우")
    void test3() {
        // given
        var sender = defaultSender();
        var receiver = defaultReceiver();
        var team = defaultTeam();

        var feedbackType = FeedbackType.IDENTIFIED;
        var feedbackFeeling = FeedbackFeeling.POSITIVE;
        var objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
        var subjectiveFeedback = "좋아요";

        var command = new SendFrequentFeedbackCommand(sender.getId(), receiver.getId(), team.getId(), feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);

        givenMembershipWillExists(team.getId(), sender.getId());

        // when
        assertThatThrownBy(() -> sendFrequentFeedbackService.sendFrequentFeedback(command))
                .isInstanceOf(MembershipNotFound.class);

        // then
    }


    @Test
    @DisplayName("수시 피드백 전송 실패 - 기분에 맞지 않는 객관식 피드백이 있을 경우")
    void test6() {
        // given
        var sender = defaultSender();
        var receiver = defaultReceiver();
        var team = defaultTeam();

        FeedbackType feedbackType = FeedbackType.IDENTIFIED;
        FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
        List<ObjectiveFeedback> objectiveFeedbacks = FeedbackFeeling.CONSTRUCTIVE.getObjectiveFeedbacks().subList(0, 1);
        String subjectiveFeedback = "좋아요";

        var command = new SendFrequentFeedbackCommand(sender.getId(), receiver.getId(), team.getId(), feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);

        givenMembershipWillExists(team.getId(), sender.getId());
        givenMembershipWillExists(team.getId(), receiver.getId());

        givenTeamWillLoad(team);
        givenSenderWillLoad(sender);
        givenReceiverWillLoad(receiver);

        // when & then
        assertThatThrownBy(() -> sendFrequentFeedbackService.sendFrequentFeedback(command))
                .isInstanceOf(IllegalArgumentException.class);

    }


    private void givenMembershipWillExists(Long teamId, Long memberId) {
        when(membershipValidatePort.hasMembership(teamId, memberId)).thenReturn(true);
    }

    private void givenTeamWillLoad(AssociatedTeam team) {
        when(loadTeamPort.loadTeam(team.getId())).thenReturn(team);
    }

    private void givenSenderWillLoad(Sender sender) {
        when(loadSenderPort.loadSender(sender.getId())).thenReturn(sender);
    }

    private void givenReceiverWillLoad(Receiver receiver) {
        when(loadReceiverPort.loadReceiver(receiver.getId())).thenReturn(receiver);
    }
}