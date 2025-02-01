package com.feedhanjum.back_end.feedback.service;

import com.feedhanjum.back_end.event.EventPublisher;
import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackFeeling;
import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.feedback.domain.ObjectiveFeedback;
import com.feedhanjum.back_end.feedback.event.FeedbackLikedEvent;
import com.feedhanjum.back_end.feedback.event.FrequentFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.event.RegularFeedbackCreatedEvent;
import com.feedhanjum.back_end.feedback.repository.FeedbackRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.domain.RegularFeedbackRequest;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.event.RegularFeedbackRequestCreatedEvent;
import com.feedhanjum.back_end.schedule.exception.NoRegularFeedbackRequestException;
import com.feedhanjum.back_end.schedule.repository.RegularFeedbackRequestRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleMemberRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.team.domain.FrequentFeedbackRequest;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.domain.TeamMember;
import com.feedhanjum.back_end.team.event.FrequentFeedbackRequestCreatedEvent;
import com.feedhanjum.back_end.team.repository.FrequentFeedbackRequestRepository;
import com.feedhanjum.back_end.team.repository.TeamMemberRepository;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private ScheduleMemberRepository scheduleMemberRepository;
    @Mock
    private FrequentFeedbackRequestRepository frequentFeedbackRequestRepository;
    @Mock
    private RegularFeedbackRequestRepository regularFeedbackRequestRepository;
    @Mock
    private EventPublisher eventPublisher;
    @InjectMocks
    private FeedbackService feedbackService;

    @Nested
    @DisplayName("sendFrequentFeedback 메서드 테스트")
    class SendFrequentFeedbackTest {
        @Test
        @DisplayName("수시 피드백 전송 성공")
        void test1() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            Member sender = mock();
            Member receiver = mock();
            Team team = mock();

            FeedbackType feedbackType = FeedbackType.IDENTIFIED;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
            String subjectiveFeedback = "좋아요";
            when(memberRepository.findById(senderId)).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(feedbackRepository.save(any(Feedback.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // when
            Feedback feedback = feedbackService.sendFrequentFeedback(senderId, receiverId, teamId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);
            // then
            assertThat(feedback.getSender()).isEqualTo(sender);
            assertThat(feedback.getReceiver()).isEqualTo(receiver);
            assertThat(feedback.getTeam()).isEqualTo(team);
            assertThat(feedback.getFeedbackType()).isEqualTo(feedbackType);
            assertThat(feedback.getFeedbackFeeling()).isEqualTo(feedbackFeeling);
            assertThat(feedback.getObjectiveFeedbacks())
                    .containsExactlyInAnyOrderElementsOf(objectiveFeedbacks);
            assertThat(feedback.getSubjectiveFeedback()).isEqualTo(subjectiveFeedback);
            assertThat(feedback.isLiked()).isFalse();

            verify(eventPublisher).publishEvent(any(FrequentFeedbackCreatedEvent.class));
        }

        @Test
        @DisplayName("수시 피드백 전송 실패 - sender가 없을 경우")
        void test2() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;

            FeedbackType feedbackType = FeedbackType.IDENTIFIED;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
            String subjectiveFeedback = "좋아요";
            when(memberRepository.findById(senderId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.sendFrequentFeedback(senderId, receiverId, teamId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackCreatedEvent.class));
        }

        @Test
        @DisplayName("수시 피드백 전송 실패 - receiver가 없을 경우")
        void test3() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            Member sender = mock();

            FeedbackType feedbackType = FeedbackType.IDENTIFIED;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
            String subjectiveFeedback = "좋아요";
            when(memberRepository.findById(senderId)).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiverId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.sendFrequentFeedback(senderId, receiverId, teamId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackCreatedEvent.class));
        }

        @Test
        @DisplayName("수시 피드백 전송 실패 - team이 없을 경우")
        void test4() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            Member sender = mock();
            Member receiver = mock();

            FeedbackType feedbackType = FeedbackType.IDENTIFIED;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
            String subjectiveFeedback = "좋아요";
            when(memberRepository.findById(senderId)).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.sendFrequentFeedback(senderId, receiverId, teamId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackCreatedEvent.class));
        }

        @Test
        @DisplayName("수시 피드백 전송 실패 - 기분에 맞지 않는 객관식 피드백이 있을 경우")
        void test6() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            Member sender = mock();
            Member receiver = mock();
            Team team = mock();

            FeedbackType feedbackType = FeedbackType.IDENTIFIED;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = FeedbackFeeling.CONSTRUCTIVE.getObjectiveFeedbacks().subList(0, 1);
            String subjectiveFeedback = "좋아요";
            when(memberRepository.findById(senderId)).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

            // when & then
            assertThatThrownBy(() -> feedbackService.sendFrequentFeedback(senderId, receiverId, teamId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
                    .isInstanceOf(IllegalArgumentException.class);

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackCreatedEvent.class));
        }

        @Test
        @DisplayName("수시 피드백 전송 실패 - 객관식 피드백 개수가 1~5개가 아닌 경우")
        void test7() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            Member sender = mock();
            Member receiver = mock();
            Team team = mock();

            FeedbackType feedbackType = FeedbackType.IDENTIFIED;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = FeedbackFeeling.CONSTRUCTIVE.getObjectiveFeedbacks().subList(0, 6);
            String subjectiveFeedback = "좋아요";
            when(memberRepository.findById(senderId)).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

            // when & then
            assertThatThrownBy(() -> feedbackService.sendFrequentFeedback(senderId, receiverId, teamId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
                    .isInstanceOf(IllegalArgumentException.class);

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackCreatedEvent.class));
        }

    }

    @Nested
    @DisplayName("requestFrequentFeedback 메서드 테스트")
    class RequestFrequentFeedbackTest {
        @Test
        @DisplayName("수시 피드백 요청 성공")
        void test1() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            String requestedContent = "좋아요";
            Member sender = mock();
            Member receiver = mock();
            Team team = mock();
            TeamMember senderTeamMember = mock();
            TeamMember receiverTeamMember = mock();

            when(memberRepository.findById(senderId)).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(teamMemberRepository.findByMemberIdAndTeamId(senderId, teamId)).thenReturn(Optional.of(senderTeamMember));
            when(teamMemberRepository.findByMemberIdAndTeamId(receiverId, teamId)).thenReturn(Optional.of(receiverTeamMember));

            // when
            FrequentFeedbackRequest result = feedbackService
                    .requestFrequentFeedback(senderId, teamId, receiverId, requestedContent);

            // then
            assertThat(result.getRequestedContent()).isEqualTo(requestedContent);
            assertThat(result.getTeamMember()).isEqualTo(receiverTeamMember);
            assertThat(result.getRequester()).isEqualTo(sender);
            assertThat(result.getCreatedAt()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
            verify(frequentFeedbackRequestRepository, times(1)).save(result);
            verify(eventPublisher, times(1)).publishEvent(any(FrequentFeedbackRequestCreatedEvent.class));

        }

        @Test
        @DisplayName("수시 피드백 요청 실패 - sender가 없을 경우")
        void test2() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            String requestedContent = "좋아요";

            when(memberRepository.findById(senderId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.requestFrequentFeedback(senderId, teamId, receiverId, requestedContent))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackRequestCreatedEvent.class));

        }

        @Test
        @DisplayName("수시 피드백 요청 실패 - receiver가 없을 경우")
        void test3() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            String requestedContent = "좋아요";
            Member sender = mock();

            when(memberRepository.findById(senderId)).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiverId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.requestFrequentFeedback(senderId, teamId, receiverId, requestedContent))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackRequestCreatedEvent.class));

        }

        @Test
        @DisplayName("수시 피드백 요청 실패 - team이 없을 경우")
        void test4() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            String requestedContent = "좋아요";
            Member sender = mock();
            Member receiver = mock();

            when(memberRepository.findById(senderId)).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.requestFrequentFeedback(senderId, teamId, receiverId, requestedContent))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackRequestCreatedEvent.class));

        }

        @Test
        @DisplayName("수시 피드백 요청 실패 - sender가 team에 속하지 않았을 경우")
        void test5() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            String requestedContent = "좋아요";
            Member sender = mock();
            Member receiver = mock();
            Team team = mock();

            when(memberRepository.findById(senderId)).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(teamMemberRepository.findByMemberIdAndTeamId(senderId, teamId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.requestFrequentFeedback(senderId, teamId, receiverId, requestedContent))
                    .isInstanceOf(EntityNotFoundException.class);


            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackRequestCreatedEvent.class));
        }

        @Test
        @DisplayName("수시 피드백 요청 실패 - receiver가 team에 속하지 않았을 경우")
        void test6() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long teamId = 3L;
            String requestedContent = "좋아요";
            Member sender = mock();
            Member receiver = mock();
            Team team = mock();
            TeamMember senderTeamMember = mock();

            when(memberRepository.findById(senderId)).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(teamMemberRepository.findByMemberIdAndTeamId(senderId, teamId)).thenReturn(Optional.of(senderTeamMember));
            when(teamMemberRepository.findByMemberIdAndTeamId(receiverId, teamId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.requestFrequentFeedback(senderId, teamId, receiverId, requestedContent))
                    .isInstanceOf(EntityNotFoundException.class);


            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackRequestCreatedEvent.class));

        }
    }

    @Nested
    @DisplayName("getFrequentFeedbackRequests 메서드 테스트")
    class GetFrequentFeedbackRequestTest {
        @Test
        @DisplayName("수시 피드백 요청 조회 성공")
        void test1() {
            // given
            Long receiverId = 1L;
            Long teamId = 2L;
            Member receiver = mock();
            Team team = mock();
            TeamMember teamMember = mock();
            List<FrequentFeedbackRequest> requests = List.of(mock(), mock());


            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(teamMemberRepository.findByMemberIdAndTeamId(receiverId, teamId)).thenReturn(Optional.of(teamMember));
            when(frequentFeedbackRequestRepository.findByTeamMember(teamMember)).thenReturn(requests);


            // when
            List<FrequentFeedbackRequest> result = feedbackService.getFrequentFeedbackRequests(receiverId, teamId);

            // then
            assertThat(result).isEqualTo(requests);
        }

        @Test
        @DisplayName("수시 피드백 요청 조회 실패 - receiver가 없을 경우")
        void test2() {
            // given
            Long receiverId = 1L;
            Long teamId = 2L;

            when(memberRepository.findById(receiverId)).thenReturn(Optional.empty());


            // when & then
            assertThatThrownBy(() -> feedbackService.getFrequentFeedbackRequests(receiverId, teamId))
                    .isInstanceOf(EntityNotFoundException.class);

        }

        @Test
        @DisplayName("수시 피드백 요청 조회 실패 - team이 없을 경우")
        void test3() {
            // given
            Long receiverId = 1L;
            Long teamId = 2L;
            Member receiver = mock();


            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.getFrequentFeedbackRequests(receiverId, teamId))
                    .isInstanceOf(EntityNotFoundException.class);

        }

        @Test
        @DisplayName("수시 피드백 요청 조회 실패 - receiver가 team에 속하지 않았을 경우")
        void test4() {
            // given
            Long receiverId = 1L;
            Long teamId = 2L;
            Member receiver = mock();
            Team team = mock();


            when(memberRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
            when(teamMemberRepository.findByMemberIdAndTeamId(receiverId, teamId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.getFrequentFeedbackRequests(receiverId, teamId))
                    .isInstanceOf(EntityNotFoundException.class);

        }
    }

    @Nested
    @DisplayName("sendRegularFeedback 메서드 테스트")
    class SendRegularFeedbackTest {
        @Test
        @DisplayName("정기 피드백 전송 성공")
        void test1() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long scheduleId = 3L;
            Member sender = mock();
            Member receiver = mock();
            Team team = mock();
            Schedule schedule = mock();
            ScheduleMember senderMember = mock();
            ScheduleMember receiverMember = mock();
            RegularFeedbackRequest request = mock();


            when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.of(senderMember));
            when(scheduleMemberRepository.findByMemberIdAndScheduleId(receiverId, scheduleId)).thenReturn(Optional.of(receiverMember));

            when(senderMember.getMember()).thenReturn(sender);
            when(receiverMember.getMember()).thenReturn(receiver);
            when(senderMember.getSchedule()).thenReturn(schedule);

            when(regularFeedbackRequestRepository.findByRequesterAndScheduleMember(receiver, senderMember)).thenReturn(Optional.of(request));
            when(schedule.getTeam()).thenReturn(team);

            when(feedbackRepository.save(any(Feedback.class))).thenAnswer(invocation -> invocation.getArgument(0));

            FeedbackType feedbackType = FeedbackType.IDENTIFIED;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
            String subjectiveFeedback = "좋아요";


            // when
            Feedback feedback = feedbackService.sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback);
            // then
            assertThat(feedback.getSender()).isEqualTo(sender);
            assertThat(feedback.getReceiver()).isEqualTo(receiver);
            assertThat(feedback.getTeam()).isEqualTo(team);
            assertThat(feedback.getFeedbackType()).isEqualTo(feedbackType);
            assertThat(feedback.getFeedbackFeeling()).isEqualTo(feedbackFeeling);
            assertThat(feedback.getObjectiveFeedbacks())
                    .containsExactlyInAnyOrderElementsOf(objectiveFeedbacks);
            assertThat(feedback.getSubjectiveFeedback()).isEqualTo(subjectiveFeedback);
            assertThat(feedback.isLiked()).isFalse();

            verify(regularFeedbackRequestRepository).delete(request);
            verify(eventPublisher).publishEvent(any(RegularFeedbackCreatedEvent.class));
        }

        @Test
        @DisplayName("정기 피드백 전송 실패 - sender가 일정에 속해있지 않을 경우")
        void test2() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long scheduleId = 3L;

            when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.empty());

            FeedbackType feedbackType = FeedbackType.IDENTIFIED;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
            String subjectiveFeedback = "좋아요";

            // when & then
            assertThatThrownBy(() -> feedbackService
                    .sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(RegularFeedbackCreatedEvent.class));

        }

        @Test
        @DisplayName("정기 피드백 전송 실패 - receiver가 없을 경우")
        void test3() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long scheduleId = 3L;

            when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.empty());

            FeedbackType feedbackType = FeedbackType.IDENTIFIED;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
            String subjectiveFeedback = "좋아요";

            // when & then
            assertThatThrownBy(() -> feedbackService
                    .sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(RegularFeedbackCreatedEvent.class));
        }

        @Test
        @DisplayName("정기 피드백 전송 실패 - 정기 피드백 요청이 없을 경우")
        void test4() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long scheduleId = 3L;
            Member sender = mock();
            Member receiver = mock();
            Schedule schedule = mock();
            ScheduleMember senderMember = mock();
            ScheduleMember receiverMember = mock();


            when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.of(senderMember));
            when(scheduleMemberRepository.findByMemberIdAndScheduleId(receiverId, scheduleId)).thenReturn(Optional.of(receiverMember));

            when(senderMember.getMember()).thenReturn(sender);
            when(receiverMember.getMember()).thenReturn(receiver);
            when(senderMember.getSchedule()).thenReturn(schedule);

            when(regularFeedbackRequestRepository.findByRequesterAndScheduleMember(receiver, senderMember)).thenReturn(Optional.empty());

            FeedbackType feedbackType = FeedbackType.IDENTIFIED;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.POSITIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 2);
            String subjectiveFeedback = "좋아요";

            // when & then
            assertThatThrownBy(() -> feedbackService.sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
                    .isInstanceOf(NoRegularFeedbackRequestException.class);

            verify(regularFeedbackRequestRepository, never()).delete(any());
            verify(eventPublisher, never()).publishEvent(any(RegularFeedbackCreatedEvent.class));
        }

        @Test
        @DisplayName("정기 피드백 전송 실패 - 기분에 맞지 않는 객관식 피드백이 있을 경우")
        void test6() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long scheduleId = 3L;
            Member sender = mock();
            Member receiver = mock();
            Team team = mock();
            Schedule schedule = mock();
            ScheduleMember senderMember = mock();
            ScheduleMember receiverMember = mock();
            RegularFeedbackRequest request = mock();


            when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.of(senderMember));
            when(scheduleMemberRepository.findByMemberIdAndScheduleId(receiverId, scheduleId)).thenReturn(Optional.of(receiverMember));

            when(senderMember.getMember()).thenReturn(sender);
            when(receiverMember.getMember()).thenReturn(receiver);
            when(senderMember.getSchedule()).thenReturn(schedule);

            when(regularFeedbackRequestRepository.findByRequesterAndScheduleMember(receiver, senderMember)).thenReturn(Optional.of(request));
            when(schedule.getTeam()).thenReturn(team);

            FeedbackType feedbackType = FeedbackType.ANONYMOUS;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.CONSTRUCTIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = FeedbackFeeling.POSITIVE.getObjectiveFeedbacks().subList(0, 2);
            String subjectiveFeedback = "좋아요";

            // when & then
            assertThatThrownBy(() -> feedbackService.sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
                    .isInstanceOf(IllegalArgumentException.class);

            verify(eventPublisher, never()).publishEvent(any(RegularFeedbackCreatedEvent.class));
        }

        @Test
        @DisplayName("정기 피드백 전송 실패 - 객관식 피드백 개수가 1~5개가 아닌 경우")
        void test7() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            Long scheduleId = 3L;
            Member sender = mock();
            Member receiver = mock();
            Team team = mock();
            Schedule schedule = mock();
            ScheduleMember senderMember = mock();
            ScheduleMember receiverMember = mock();
            RegularFeedbackRequest request = mock();


            when(scheduleMemberRepository.findByMemberIdAndScheduleId(senderId, scheduleId)).thenReturn(Optional.of(senderMember));
            when(scheduleMemberRepository.findByMemberIdAndScheduleId(receiverId, scheduleId)).thenReturn(Optional.of(receiverMember));

            when(senderMember.getMember()).thenReturn(sender);
            when(receiverMember.getMember()).thenReturn(receiver);
            when(senderMember.getSchedule()).thenReturn(schedule);

            when(regularFeedbackRequestRepository.findByRequesterAndScheduleMember(receiver, senderMember)).thenReturn(Optional.of(request));
            when(schedule.getTeam()).thenReturn(team);

            FeedbackType feedbackType = FeedbackType.ANONYMOUS;
            FeedbackFeeling feedbackFeeling = FeedbackFeeling.CONSTRUCTIVE;
            List<ObjectiveFeedback> objectiveFeedbacks = feedbackFeeling.getObjectiveFeedbacks().subList(0, 6);
            String subjectiveFeedback = "좋아요";

            // when & then
            assertThatThrownBy(() -> feedbackService.sendRegularFeedback(senderId, receiverId, scheduleId, feedbackType, feedbackFeeling, objectiveFeedbacks, subjectiveFeedback))
                    .isInstanceOf(IllegalArgumentException.class);

            verify(eventPublisher, never()).publishEvent(any(RegularFeedbackCreatedEvent.class));
        }
    }

    @Nested
    @DisplayName("likeFeedback 메서드 테스트")
    class LikeFeedbackTest {
        @Test
        @DisplayName("피드백 좋아요 성공")
        void test1() {
            // given
            Long feedbackId = 1L;
            Long memberId = 2L;
            Feedback feedback = mock();
            Member member = mock();

            when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            when(feedback.isReceiver(member)).thenReturn(true);

            // when
            feedbackService.likeFeedback(feedbackId, memberId);

            // then
            verify(feedback).like();
            verify(eventPublisher).publishEvent(any(FeedbackLikedEvent.class));
        }

        @Test
        @DisplayName("피드백 좋아요 실패 - feedback이 없을 경우")
        void test2() {
            // given
            Long feedbackId = 1L;
            Long memberId = 2L;

            when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.likeFeedback(feedbackId, memberId))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(FeedbackLikedEvent.class));
        }

        @Test
        @DisplayName("피드백 좋아요 실패 - receiver가 없을 경우")
        void test3() {
            // given
            Long feedbackId = 1L;
            Long memberId = 2L;
            Feedback feedback = mock();

            when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
            when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.likeFeedback(feedbackId, memberId))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(FeedbackLikedEvent.class));
        }

        @Test
        @DisplayName("피드백 좋아요 실패 - receiver가 아닐 경우")
        void test4() {
            // given
            Long feedbackId = 1L;
            Long memberId = 2L;
            Feedback feedback = mock();
            Member member = mock();

            when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            when(feedback.isReceiver(member)).thenReturn(false);

            // when & then
            assertThatThrownBy(() -> feedbackService.likeFeedback(feedbackId, memberId))
                    .isInstanceOf(SecurityException.class);

            verify(eventPublisher, never()).publishEvent(any(FeedbackLikedEvent.class));
        }
    }

    @Nested
    @DisplayName("unlikeFeedback 메서드 테스트")
    class UnlikeFeedbackTest {
        @Test
        @DisplayName("피드백 좋아요 취소 성공")
        void test1() {
            // given
            Long feedbackId = 1L;
            Long memberId = 2L;
            Feedback feedback = mock();
            Member member = mock();

            when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            when(feedback.isReceiver(member)).thenReturn(true);

            // when
            feedbackService.unlikeFeedback(feedbackId, memberId);

            // then
            verify(feedback).unlike();
        }

        @Test
        @DisplayName("피드백 좋아요 실패 - feedback이 없을 경우")
        void test2() {
            // given
            Long feedbackId = 1L;
            Long memberId = 2L;

            when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.unlikeFeedback(feedbackId, memberId))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(eventPublisher, never()).publishEvent(any(FeedbackLikedEvent.class));
        }

        @Test
        @DisplayName("피드백 좋아요 취소 실패 - receiver가 없을 경우")
        void test3() {
            // given
            Long feedbackId = 1L;
            Long memberId = 2L;
            Feedback feedback = mock();

            when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
            when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.unlikeFeedback(feedbackId, memberId))
                    .isInstanceOf(EntityNotFoundException.class);

        }

        @Test
        @DisplayName("피드백 좋아요 취소 실패 - receiver가 아닐 경우")
        void test4() {
            // given
            Long feedbackId = 1L;
            Long memberId = 2L;
            Feedback feedback = mock();
            Member member = mock();

            when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            when(feedback.isReceiver(member)).thenReturn(false);

            // when & then
            assertThatThrownBy(() -> feedbackService.unlikeFeedback(feedbackId, memberId))
                    .isInstanceOf(SecurityException.class);
        }
    }

    @Nested
    @DisplayName("createRegularFeedbackRequests 메서드 테스트")
    class CreateRegularFeedbackRequestsTest {
        @Test
        @DisplayName("정기 피드백 요청 생성 성공")
        void test1() {
            // given
            Long scheduleId = 1L;
            Long memberId1 = 2L;
            Long memberId2 = 3L;
            Long memberId3 = 4L;
            Schedule schedule = mock();
            LocalDateTime endTime = LocalDateTime.now().minusMinutes(1);
            ScheduleMember scheduleMember1 = mock();
            ScheduleMember scheduleMember2 = mock();
            ScheduleMember scheduleMember3 = mock();
            Member member1 = mock();
            Member member2 = mock();
            Member member3 = mock();

            when(scheduleRepository.findByIdWithMembers(scheduleId)).thenReturn(Optional.of(schedule));
            when(schedule.isEnd()).thenReturn(true);
            when(schedule.getEndTime()).thenReturn(endTime);
            when(schedule.getScheduleMembers()).thenReturn(List.of(scheduleMember1, scheduleMember2, scheduleMember3));
            when(scheduleMember1.getMember()).thenReturn(member1);
            when(scheduleMember2.getMember()).thenReturn(member2);
            when(scheduleMember3.getMember()).thenReturn(member3);
            when(scheduleMember1.getRegularFeedbackRequests()).thenReturn(new ArrayList<>());
            when(scheduleMember2.getRegularFeedbackRequests()).thenReturn(new ArrayList<>());
            when(scheduleMember3.getRegularFeedbackRequests()).thenReturn(new ArrayList<>());
            when(member1.getId()).thenReturn(memberId1);
            when(member2.getId()).thenReturn(memberId2);
            when(member3.getId()).thenReturn(memberId3);

            // when
            feedbackService.createRegularFeedbackRequests(scheduleId);

            // then
            ArgumentCaptor<List<RegularFeedbackRequest>> requestsCaptor = ArgumentCaptor.captor();
            verify(regularFeedbackRequestRepository).saveAll(requestsCaptor.capture());
            List<RegularFeedbackRequest> requests = requestsCaptor.getValue();
            assertThat(requests).hasSize(6);
            assertThat(requests)
                    .filteredOn(r -> r.getScheduleMember() == scheduleMember1)
                    .extracting(RegularFeedbackRequest::getRequester)
                    .containsExactlyInAnyOrder(member2, member3);
            assertThat(requests)
                    .filteredOn(r -> r.getScheduleMember() == scheduleMember2)
                    .extracting(RegularFeedbackRequest::getRequester)
                    .containsExactlyInAnyOrder(member1, member3);

            assertThat(requests)
                    .filteredOn(r -> r.getScheduleMember() == scheduleMember3)
                    .extracting(RegularFeedbackRequest::getRequester)
                    .containsExactlyInAnyOrder(member1, member2);

            ArgumentCaptor<RegularFeedbackRequestCreatedEvent> eventCaptor = ArgumentCaptor.captor();
            verify(eventPublisher, times(3)).publishEvent(eventCaptor.capture());
            List<RegularFeedbackRequestCreatedEvent> events = eventCaptor.getAllValues();
            assertThat(events).extracting(RegularFeedbackRequestCreatedEvent::getReceiverId)
                    .containsExactlyInAnyOrder(memberId1, memberId2, memberId3);
            assertThat(events).extracting(RegularFeedbackRequestCreatedEvent::getScheduleId)
                    .containsOnly(scheduleId);
        }

        @Test
        @DisplayName("정기 피드백 요청 생성 실패 - schedule이 없을 경우")
        void test2() {
            // given
            Long scheduleId = 1L;

            when(scheduleRepository.findByIdWithMembers(scheduleId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.createRegularFeedbackRequests(scheduleId))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(regularFeedbackRequestRepository, never()).saveAll(any());
            verify(eventPublisher, never()).publishEvent(any(RegularFeedbackRequestCreatedEvent.class));
        }

        @Test
        @DisplayName("정기 피드백 요청 생성 실패 - schedule이 종료되지 않았을 경우")
        void test3() {
            // given
            Long scheduleId = 1L;
            Schedule schedule = mock();

            when(scheduleRepository.findByIdWithMembers(scheduleId)).thenReturn(Optional.of(schedule));
            when(schedule.isEnd()).thenReturn(false);

            // when & then
            assertThatThrownBy(() -> feedbackService.createRegularFeedbackRequests(scheduleId))
                    .isInstanceOf(IllegalStateException.class);

            verify(regularFeedbackRequestRepository, never()).saveAll(any());
            verify(eventPublisher, never()).publishEvent(any(RegularFeedbackRequestCreatedEvent.class));
        }
    }

    @Nested
    @DisplayName("skipRegularFeedback 메서드 테스트")
    class SkipRegularFeedbackTest {
        @Test
        @DisplayName("정기 피드백 건너뛰기 성공")
        void test1() {
            // given
            Long scheduleId = 1L;
            Long memberId = 2L;
            ScheduleMember scheduleMember = mock();

            when(scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, scheduleId)).thenReturn(Optional.of(scheduleMember));

            // when
            feedbackService.skipRegularFeedback(scheduleId, memberId);

            // then
            verify(regularFeedbackRequestRepository).deleteAllByScheduleMember(scheduleMember);
        }

        @Test
        @DisplayName("정기 피드백 건너뛰기 실패 - 일정에 속한 member가 없을 경우")
        void test2() {
            // given
            Long scheduleId = 1L;
            Long memberId = 2L;

            when(scheduleMemberRepository.findByMemberIdAndScheduleId(memberId, scheduleId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.skipRegularFeedback(scheduleId, memberId))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(regularFeedbackRequestRepository, never()).deleteAllByScheduleMember(any());
        }
    }
}