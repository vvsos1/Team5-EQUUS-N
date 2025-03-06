package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.core.event.EventPublisher;
import com.feedhanjum.back_end.core.event.Events;
import com.feedhanjum.back_end.feedback.application.port.in.command.SendRegularFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.DeleteRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.schedule.ParticipationValidatePort;
import com.feedhanjum.back_end.feedback.application.port.out.team.LoadTeamFromSchedulePort;
import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import com.feedhanjum.back_end.feedback.domain.feedback.*;
import com.feedhanjum.back_end.feedback.event.RegularFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.exception.ParticipationNotFound;
import com.feedhanjum.back_end.feedback.exception.RegularFeedbackRequestNotFoundException;
import com.feedhanjum.back_end.feedback.test.FeedbackFixture;
import com.feedhanjum.back_end.feedback.test.SimpleFeedbackIdGenerator;
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
import java.util.Optional;

import static com.feedhanjum.back_end.feedback.test.FeedbackFixture.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendRegularFeedbackServiceTest {
    @Mock
    LoadRegularFeedbackRequestPort loadRegularFeedbackRequestPort;
    @Spy
    FeedbackIdGenerator feedbackIdGenerator = new SimpleFeedbackIdGenerator();
    @Mock
    ParticipationValidatePort participationValidatePort;
    @Mock
    LoadTeamFromSchedulePort loadTeamFromSchedulePortPort;
    @Mock
    LoadMemberPort loadMemberPort;
    @Spy
    Clock clock = FeedbackFixture.defaultClock();
    @Mock
    SaveFeedbackPort saveFeedbackPort;
    @Mock
    DeleteRegularFeedbackRequestPort deleteRegularFeedbackRequestPort;

    @InjectMocks
    SendRegularFeedbackService sendRegularFeedbackService;

    EventPublisher eventPublisher;

    @BeforeEach
    void setup() {
        eventPublisher = mock();
        Events.setPublisher(eventPublisher);
    }

    @Test
    @DisplayName("정기 피드백 전송 성공")
    void test1() {
        // given
        var sender = defaultSender();
        var receiver = defaultReceiver();
        var team = defaultTeam();
        var schedule = defaultSchedule(team.getId());

        FeedbackType feedbackType = FeedbackType.IDENTIFIED;
        FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
        List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
        String subjectiveFeedback = "좋아요";

        var command = new SendRegularFeedbackCommand(sender.getId(), receiver.getId(), schedule.getId(), feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);

        var request = createRegularFeedbackRequest(receiver, schedule, sender);

        givenRequestWillLoad(request);

        givenParticipationWillExists(schedule.getId(), sender.getId());
        givenParticipationWillExists(schedule.getId(), receiver.getId());

        givenTeamFromScheduleWillLoad(schedule.getId(), team);
        givenMemberWillLoad(sender);
        givenMemberWillLoad(receiver);

        // when
        sendRegularFeedbackService.sendRegularFeedback(command);

        // then
        verify(saveFeedbackPort).saveFeedback(any(Feedback.class));
        verify(eventPublisher).publishEvent(any(RegularFeedbackCreatedEvent.class));
        verifyRequestWasDeleted(request);
    }

    @Test
    @DisplayName("정기 피드백 전송 실패 - sender가 일정에 속해있지 않을 경우")
    void test2() {
        // given
        var sender = defaultSender();
        var receiver = defaultReceiver();
        var team = defaultTeam();
        var schedule = defaultSchedule(team.getId());

        FeedbackType feedbackType = FeedbackType.IDENTIFIED;
        FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
        List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
        String subjectiveFeedback = "좋아요";

        var command = new SendRegularFeedbackCommand(sender.getId(), receiver.getId(), schedule.getId(), feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);

        var request = createRegularFeedbackRequest(receiver, schedule, sender);

        givenRequestWillLoad(request);

        // when & then
        assertThatThrownBy(() -> sendRegularFeedbackService.sendRegularFeedback(command))
                .isInstanceOf(ParticipationNotFound.class);

    }

    @Test
    @DisplayName("정기 피드백 전송 실패 - receiver가 없을 경우")
    void test3() {
        // given
        var sender = defaultSender();
        var receiver = defaultReceiver();
        var team = defaultTeam();
        var schedule = defaultSchedule(team.getId());

        FeedbackType feedbackType = FeedbackType.IDENTIFIED;
        FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
        List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
        String subjectiveFeedback = "좋아요";

        var command = new SendRegularFeedbackCommand(sender.getId(), receiver.getId(), schedule.getId(), feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);

        var request = createRegularFeedbackRequest(receiver, schedule, sender);

        givenRequestWillLoad(request);

        // when & then
        assertThatThrownBy(() -> sendRegularFeedbackService.sendRegularFeedback(command))
                .isInstanceOf(ParticipationNotFound.class);
    }

    @Test
    @DisplayName("정기 피드백 전송 실패 - 정기 피드백 요청이 없을 경우")
    void test4() {
        // given
        var sender = defaultSender();
        var receiver = defaultReceiver();
        var team = defaultTeam();
        var schedule = defaultSchedule(team.getId());

        FeedbackType feedbackType = FeedbackType.IDENTIFIED;
        FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
        List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
        String subjectiveFeedback = "좋아요";

        var command = new SendRegularFeedbackCommand(sender.getId(), receiver.getId(), schedule.getId(), feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);

        givenRequestWillNotLoad();

        // when & then
        assertThatThrownBy(() -> sendRegularFeedbackService.sendRegularFeedback(command))
                .isInstanceOf(RegularFeedbackRequestNotFoundException.class);
    }


    private void givenParticipationWillExists(Long scheduleId, Long memberId) {
        when(participationValidatePort.hasParticipation(scheduleId, memberId)).thenReturn(true);
    }

    private void givenTeamFromScheduleWillLoad(Long scheduleId, AssociatedTeam team) {
        when(loadTeamFromSchedulePortPort.loadTeamFromSchedule(scheduleId)).thenReturn(Optional.of(team));
    }

    private void givenMemberWillLoad(FeedbackMember member) {
        when(loadMemberPort.loadMember(member.getId())).thenReturn(Optional.of(member));
    }

    private void givenRequestWillLoad(RegularFeedbackRequest request) {
        when(loadRegularFeedbackRequestPort.loadRegularFeedbackRequest(
                request.getRequester().getId(),
                request.getSchedule().getId(),
                request.getReceiver().getId()))
                .thenReturn(Optional.of(request));
    }

    private void givenRequestWillNotLoad() {
        when(loadRegularFeedbackRequestPort.loadRegularFeedbackRequest(any(), any(), any()))
                .thenReturn(Optional.empty());
    }


    private void verifyRequestWasDeleted(RegularFeedbackRequest request) {
        verify(deleteRegularFeedbackRequestPort).deleteRegularFeedbackRequest(request.getId());
    }
}