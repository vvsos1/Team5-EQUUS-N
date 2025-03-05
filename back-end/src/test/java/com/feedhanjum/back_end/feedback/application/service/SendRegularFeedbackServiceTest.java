package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.out.LoadMemberPort;
import com.feedhanjum.back_end.feedback.application.port.out.LoadTeamFromSchedulePort;
import com.feedhanjum.back_end.feedback.application.port.out.ParticipationValidatePort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackIdGenerator;
import com.feedhanjum.back_end.feedback.test.SimpleFeedbackIdGenerator;
import com.feedhanjum.back_end.test.util.Fixture;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendRegularFeedbackServiceTest {
    @Spy
    FeedbackIdGenerator feedbackIdGenerator = new SimpleFeedbackIdGenerator();
    @Mock
    ParticipationValidatePort participationValidatePort;
    @Mock
    LoadTeamFromSchedulePort loadTeamFromSchedulePortPort;
    @Mock
    LoadMemberPort loadMemberPort;
    @Mock
    Clock clock = Fixture.defaultClock();
    @Mock
    SaveFeedbackPort saveFeedbackPort;

    @InjectMocks
    SendRegularFeedbackService sendRegularFeedbackService;

    private void givenParticipationWillExists(Long scheduleId, Long memberId) {
        when(participationValidatePort.hasParticipation(scheduleId, memberId)).thenReturn(true);
    }

    private void givenTeamFromScheduleWillLoad(Long scheduleId, AssociatedTeam team) {
        when(loadTeamFromSchedulePortPort.loadTeamFromSchedule(scheduleId)).thenReturn(Optional.of(team));
    }

    private void givenMemberWillLoad(FeedbackMember member) {
        when(loadMemberPort.loadMember(member.getId())).thenReturn(Optional.of(member));
    }

//
//    @Test
//    @DisplayName("정기 피드백 전송 성공")
//    void test1() {
//        // given
//        var sender = defaultSender();
//        var receiver = defaultReceiver();
//        var team = defaultTeam();
//
//        var scheduleId = 10L;
//
//        FeedbackType feedbackType = FeedbackType.IDENTIFIED;
//        FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
//        List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
//        String subjectiveFeedback = "좋아요";
//
//        var command = new SendRegularFeedbackCommand(sender.getId(), receiver.getId(), scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);
//
//        givenParticipationWillExists(scheduleId, sender.getId());
//        givenParticipationWillExists(scheduleId, receiver.getId());
//
//        givenTeamFromScheduleWillLoad(scheduleId, team);
//        givenSenderWillLoad(sender);
//        givenReceiverWillLoad(receiver);
//
//        // when
//        sendRegularFeedbackService.sendRegularFeedback(command);
//
//        // then
//    }
//
//
//    @Test
//    @DisplayName("정기 피드백 전송 실패 - sender가 일정에 속해있지 않을 경우")
//    void test2() {
//        // given
//        Long senderId = 1L;
//        Long receiverId = 2L;
//        Long scheduleId = 3L;
//
//        when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.empty());
//
//        FeedbackType feedbackType = FeedbackType.IDENTIFIED;
//        FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
//        List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
//        String subjectiveFeedback = "좋아요";
//
//        // when & then
//        assertThatThrownBy(() -> feedbackService
//                .sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
//                .isInstanceOf(EntityNotFoundException.class);
//
//        verify(eventPublisher, never()).publishEvent(any(RegularFeedbackCreatedEvent.class));
//
//    }
//
//    @Test
//    @DisplayName("정기 피드백 전송 실패 - receiver가 없을 경우")
//    void test3() {
//        // given
//        Long senderId = 1L;
//        Long receiverId = 2L;
//        Long scheduleId = 3L;
//
//        when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.empty());
//
//        FeedbackType feedbackType = FeedbackType.IDENTIFIED;
//        FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
//        List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
//        String subjectiveFeedback = "좋아요";
//
//        // when & then
//        assertThatThrownBy(() -> feedbackService
//                .sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
//                .isInstanceOf(EntityNotFoundException.class);
//
//        verify(eventPublisher, never()).publishEvent(any(RegularFeedbackCreatedEvent.class));
//    }
//
//    @Test
//    @DisplayName("정기 피드백 전송 실패 - 정기 피드백 요청이 없을 경우")
//    void test4() {
//        // given
//        Long senderId = 1L;
//        Long receiverId = 2L;
//        Long scheduleId = 3L;
//        Member sender = mock();
//        Member receiver = mock();
//        Schedule schedule = mock();
//        ScheduleMember senderMember = mock();
//        ScheduleMember receiverMember = mock();
//
//
//        when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.of(senderMember));
//        when(scheduleMemberRepository.findByMemberIdAndScheduleId(receiverId, scheduleId)).thenReturn(Optional.of(receiverMember));
//
//        when(senderMember.getMember()).thenReturn(sender);
//        when(receiverMember.getMember()).thenReturn(receiver);
//        when(senderMember.getSchedule()).thenReturn(schedule);
//
//        when(regularFeedbackRequestRepository.findByRequesterAndScheduleMember(receiver, senderMember)).thenReturn(Optional.empty());
//
//        FeedbackType feedbackType = FeedbackType.IDENTIFIED;
//        FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
//        List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
//        String subjectiveFeedback = "좋아요";
//
//        // when & then
//        assertThatThrownBy(() -> feedbackService.sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
//                .isInstanceOf(NoRegularFeedbackRequestException.class);
//
//        verify(regularFeedbackRequestRepository, never()).delete(any());
//        verify(eventPublisher, never()).publishEvent(any(RegularFeedbackCreatedEvent.class));
//    }
//
//    @Test
//    @DisplayName("정기 피드백 전송 실패 - 기분에 맞지 않는 객관식 피드백이 있을 경우")
//    void test6() {
//        // given
//        Long senderId = 1L;
//        Long receiverId = 2L;
//        Long scheduleId = 3L;
//        Member sender = mock();
//        Member receiver = mock();
//        Team team = mock();
//        Schedule schedule = mock();
//        ScheduleMember senderMember = mock();
//        ScheduleMember receiverMember = mock();
//        RegularFeedbackRequest request = mock();
//
//
//        when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.of(senderMember));
//        when(scheduleMemberRepository.findByMemberIdAndScheduleId(receiverId, scheduleId)).thenReturn(Optional.of(receiverMember));
//
//        when(senderMember.getMember()).thenReturn(sender);
//        when(receiverMember.getMember()).thenReturn(receiver);
//        when(senderMember.getSchedule()).thenReturn(schedule);
//
//        when(regularFeedbackRequestRepository.findByRequesterAndScheduleMember(receiver, senderMember)).thenReturn(Optional.of(request));
//        when(schedule.getTeam()).thenReturn(team);
//
//        FeedbackType feedbackType = FeedbackType.ANONYMOUS;
//        FeedbackFeeling feedbackFeeling = FeedbackFeeling.CONSTRUCTIVE;
//        List<ObjectiveFeedback> objectiveFeedbacks = FeedbackFeeling.POSITIVE.getObjectiveFeedbacks().subList(0, 2);
//        String subjectiveFeedback = "좋아요";
//
//        // when & then
//        assertThatThrownBy(() -> feedbackService.sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
//                .isInstanceOf(IllegalArgumentException.class);
//
//        verify(eventPublisher, never()).publishEvent(any(RegularFeedbackCreatedEvent.class));
//    }
//
//    @Test
//    @DisplayName("정기 피드백 전송 실패 - 객관식 피드백 개수가 1~5개가 아닌 경우")
//    void test7() {
//        // given
//        Long senderId = 1L;
//        Long receiverId = 2L;
//        Long scheduleId = 3L;
//        Member sender = mock();
//        Member receiver = mock();
//        Team team = mock();
//        Schedule schedule = mock();
//        ScheduleMember senderMember = mock();
//        ScheduleMember receiverMember = mock();
//        RegularFeedbackRequest request = mock();
//
//
//        when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.of(senderMember));
//        when(scheduleMemberRepository.findByMemberIdAndScheduleId(receiverId, scheduleId)).thenReturn(Optional.of(receiverMember));
//
//        when(senderMember.getMember()).thenReturn(sender);
//        when(receiverMember.getMember()).thenReturn(receiver);
//        when(senderMember.getSchedule()).thenReturn(schedule);
//
//        when(regularFeedbackRequestRepository.findByRequesterAndScheduleMember(receiver, senderMember)).thenReturn(Optional.of(request));
//        when(schedule.getTeam()).thenReturn(team);
//
//        FeedbackType feedbackType = FeedbackType.ANONYMOUS;
//        FeedbackFeeling feedbackFeeling = FeedbackFeeling.CONSTRUCTIVE;
//        List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 6);
//        String subjectiveFeedback = "좋아요";
//
//        // when & then
//        assertThatThrownBy(() -> feedbackService.sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
//                .isInstanceOf(IllegalArgumentException.class);
//
//        verify(eventPublisher, never()).publishEvent(any(RegularFeedbackCreatedEvent.class));
//    }

}