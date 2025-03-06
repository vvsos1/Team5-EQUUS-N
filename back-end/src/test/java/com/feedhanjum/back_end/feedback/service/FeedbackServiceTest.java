package com.feedhanjum.back_end.feedback.service;

import com.feedhanjum.back_end.core.event.EventPublisher;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadFeedbackPort;
import com.feedhanjum.back_end.feedback.domain.FrequentFeedbackRequest;
import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackIdGenerator;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.back_end.feedback.repository.FrequentFeedbackRequestRepository;
import com.feedhanjum.back_end.feedback.test.SimpleFeedbackIdGenerator;
import com.feedhanjum.back_end.member.domain.FeedbackPreference;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.event.FrequentFeedbackRequestedEvent;
import com.feedhanjum.back_end.team.exception.TeamMembershipNotFoundException;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static com.feedhanjum.back_end.test.util.DomainTestUtils.createFeedbackWithId;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {
    @Spy
    private FeedbackIdGenerator feedbackIdGenerator = new SimpleFeedbackIdGenerator();
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private LoadFeedbackPort loadFeedbackPort;
    @Mock
    private FrequentFeedbackRequestRepository frequentFeedbackRequestRepository;
    @Mock
    private EventPublisher eventPublisher;
    @InjectMocks
    private FeedbackService feedbackService;

    @Spy
    private final Clock clock = Clock.fixed(Instant.parse("2025-01-10T12:00:00Z"), ZoneId.systemDefault());

    private final AtomicLong nextId = new AtomicLong(1L);

    private Member createMember(String name) {
        List<FeedbackPreference> feedbackPreferences = List.of(FeedbackPreference.PROGRESSIVE, FeedbackPreference.COMPLEMENTING);
        Member member = new Member(name, name + "@test.com", new ProfileImage("bg-" + name, "profile-" + name), feedbackPreferences);
        ReflectionTestUtils.setField(member, "id", nextId.getAndIncrement());
        return member;
    }

    private Team createTeam(String name, Member leader) {
        Team team = new Team(name, leader, LocalDate.now(clock).minusDays(1), LocalDate.now(clock).plusDays(1), FeedbackType.ANONYMOUS, LocalDate.now(clock));
        ReflectionTestUtils.setField(team, "id", nextId.getAndIncrement());
        return team;
    }


    @Nested
    @DisplayName("requestFrequentFeedback 메서드 테스트")
    class RequestFrequentFeedbackTest {
        @Test
        @DisplayName("수시 피드백 요청 성공")
        void test1() {
            // given
            String requestedContent = "좋아요";
            Member sender = createMember("sender");
            Member receiver = createMember("receiver");
            Team team = createTeam("team", sender);
            team.join(receiver);

            when(memberRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));

            // when
            feedbackService
                    .requestFrequentFeedback(sender.getId(), team.getId(), receiver.getId(), requestedContent);

            // then
            List<FrequentFeedbackRequest> requests = team.getFeedbackRequests(receiver);
            assertThat(requests).hasSize(1);
            FrequentFeedbackRequest result = requests.get(0);
            assertThat(result.getRequestedContent()).isEqualTo(requestedContent);
            assertThat(result.getTeam()).isEqualTo(team);
            assertThat(result.getReceiver()).isEqualTo(receiver);
            assertThat(result.getRequester()).isEqualTo(sender);
            assertThat(result.getCreatedAt()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
            verify(eventPublisher, times(1)).publishEvent(any(FrequentFeedbackRequestedEvent.class));

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

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackRequestedEvent.class));

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

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackRequestedEvent.class));

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

            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackRequestedEvent.class));

        }

        @Test
        @DisplayName("수시 피드백 요청 실패 - sender가 team에 속하지 않았을 경우")
        void test5() {
            // given
            String requestedContent = "좋아요";
            Member sender = createMember("sender");
            Member receiver = createMember("receiver");
            Team team = createTeam("team", receiver);

            when(memberRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));

            // when & then
            assertThatThrownBy(() -> feedbackService.requestFrequentFeedback(sender.getId(), team.getId(), receiver.getId(), requestedContent))
                    .isInstanceOf(TeamMembershipNotFoundException.class);


            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackRequestedEvent.class));
        }

        @Test
        @DisplayName("수시 피드백 요청 실패 - receiver가 team에 속하지 않았을 경우")
        void test6() {
            // given
            String requestedContent = "좋아요";
            Member sender = createMember("sender");
            Member receiver = createMember("receiver");
            Team team = createTeam("team", sender);

            when(memberRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
            when(memberRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));
            when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));

            // when & then
            assertThatThrownBy(() -> feedbackService.requestFrequentFeedback(sender.getId(), team.getId(), receiver.getId(), requestedContent))
                    .isInstanceOf(TeamMembershipNotFoundException.class);


            verify(eventPublisher, never()).publishEvent(any(FrequentFeedbackRequestedEvent.class));

        }
    }

    @Nested
    @DisplayName("rejectAllFrequentFeedbackRequests 메서드 테스트")
    class RejectAllFrequentFeedbackRequestsTest {
        @Test
        @DisplayName("모든 수시 피드백 요청 거절 성공")
        void test1() {
            // given
            Member receiver = createMember("receiver");
            Team team = createTeam("team", receiver);
            Member sender = createMember("sender");
            team.join(sender);
            team.requestFeedback(sender, receiver, "좋아요");
            team.requestFeedback(sender, receiver, "좋아요2");

            when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));
            when(memberRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));

            // when
            feedbackService.rejectAllFrequentFeedbackRequests(receiver.getId(), team.getId());

            // then
            assertThat(team.getFeedbackRequests(receiver)).isEmpty();
        }

        @Test
        @DisplayName("모든 수시 피드백 요청 거절 실패 - member가 team에 속하지 않았을 경우")
        void test2() {
            // given
            Member receiver = createMember("receiver");
            Team team = createTeam("team", createMember("notReceiver"));

            when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));
            when(memberRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));

            // when & then
            assertThatThrownBy(() -> feedbackService.rejectAllFrequentFeedbackRequests(receiver.getId(), team.getId()))
                    .isInstanceOf(TeamMembershipNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("deleteRelatedFrequentFeedbackRequest 메서드 테스트")
    class DeleteRelatedFrequentFeedbackRequestTest {
        @Test
        @DisplayName("연관 수시 피드백 요청 삭제 성공")
        void test1() {
            // given
            Member feedbackSender = createMember("sender");
            Member feedbackReceiver = createMember("receiver");
            Team team = createTeam("team", feedbackSender);
            team.join(feedbackReceiver);
            team.requestFeedback(feedbackSender, feedbackReceiver, "좋아요");
            team.requestFeedback(feedbackReceiver, feedbackSender, "좋아요 2");

            Feedback feedback = createFeedbackWithId(feedbackSender, feedbackReceiver, team, FeedbackType.ANONYMOUS);

            when(loadFeedbackPort.loadFeedback(feedback.getId())).thenReturn(Optional.of(feedback));
            when(memberRepository.findById(feedbackSender.getId())).thenReturn(Optional.of(feedbackSender));
            when(memberRepository.findById(feedbackReceiver.getId())).thenReturn(Optional.of(feedbackReceiver));
            when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));

            // when
            feedbackService.deleteRelatedFrequentFeedbackRequest(feedback.getId());

            // then
            assertThat(team.getFeedbackRequests(feedbackSender)).isEmpty();
        }


        @Test
        @DisplayName("연관 수시 피드백 요청 삭제 실패 - feedback이 없을 경우")
        void test2() {
            // given
            FeedbackId feedbackId = new FeedbackId(1L);

            when(loadFeedbackPort.loadFeedback(feedbackId)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> feedbackService.deleteRelatedFrequentFeedbackRequest(feedbackId))
                    .isInstanceOf(EntityNotFoundException.class);

            verify(frequentFeedbackRequestRepository, never()).delete(any());
        }

        @Test
        @DisplayName("연관 수시 피드백 요청 삭제 실패 - sender가 team에 속하지 않았을 경우")
        void test3() {
            // given
            Member receiver = createMember("receiver");
            Team team = createTeam("team", receiver);
            Member sender = createMember("sender");
            Feedback feedback = createFeedbackWithId(sender, receiver, team, FeedbackType.ANONYMOUS);

            when(loadFeedbackPort.loadFeedback(feedback.getId())).thenReturn(Optional.of(feedback));
            when(memberRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));
            when(memberRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
            when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));


            // when & then
            assertThatThrownBy(() -> feedbackService.deleteRelatedFrequentFeedbackRequest(feedback.getId()))
                    .isInstanceOf(TeamMembershipNotFoundException.class);

            verify(frequentFeedbackRequestRepository, never()).delete(any());
        }
    }
}