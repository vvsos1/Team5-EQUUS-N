package com.feedhanjum.back_end.feedback.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feedhanjum.back_end.core.dto.Paged;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.request.RequestFrequentFeedbackRequest;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.request.SendFrequentFeedbackRequest;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.request.SendRegularFeedbackRequest;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.response.FrequentFeedbackRequestResponse;
import com.feedhanjum.back_end.feedback.adapter.in.web.dto.response.RegularFeedbackRequestResponse;
import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.RequestFrequentFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.request.frequent.command.RequestFrequentFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadFeedbackPort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.frequent.LoadFrequentFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.LoadRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.application.port.out.request.regular.SaveRegularFeedbackRequestPort;
import com.feedhanjum.back_end.feedback.domain.AssociatedSchedule;
import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackFeeling;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.back_end.feedback.dto.ReceivedFeedbackDto;
import com.feedhanjum.back_end.feedback.dto.SentFeedbackDto;
import com.feedhanjum.back_end.member.domain.FeedbackPreference;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.repository.ScheduleMemberRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import static com.feedhanjum.back_end.feedback.test.FeedbackFixture.createRegularFeedbackRequest;
import static com.feedhanjum.back_end.test.util.DomainTestUtils.createFeedbackWithId;
import static com.feedhanjum.back_end.test.util.SessionTestUtil.withLoginUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test")
class FeedbackControllerTest {

    @Autowired
    private MockMvcTester mvc;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleMemberRepository scheduleMemberRepository;
    private final Clock clock = Clock.fixed(Instant.parse("2025-01-10T12:00:00Z"), ZoneId.systemDefault());
    @Autowired
    private SaveRegularFeedbackRequestPort saveRegularFeedbackRequestPort;
    @Autowired
    private LoadRegularFeedbackRequestPort loadRegularFeedbackRequestPort;
    @Autowired
    private SaveFeedbackPort saveFeedbackPort;
    @Autowired
    private LoadFeedbackPort loadFeedbackPort;
    @Autowired
    private RequestFrequentFeedbackUseCase requestFrequentFeedbackUseCase;
    @Autowired
    private LoadFrequentFeedbackRequestPort loadFrequentFeedbackRequestPort;

    private Member createMember(String name) {
        List<FeedbackPreference> feedbackPreferences = List.of(FeedbackPreference.PROGRESSIVE, FeedbackPreference.COMPLEMENTING);
        return new Member(name, name + "@test.com", new ProfileImage("bg-" + name, "profile-" + name), feedbackPreferences);
    }

    private Team createTeam(String name, Member leader) {
        return new Team(name, leader, LocalDate.now(clock).minusDays(1), LocalDate.now(clock).plusDays(1), FeedbackType.ANONYMOUS, LocalDate.now(clock));
    }

    private Schedule createSchedule(String name, Team team, Member leader, boolean isEnd) {
        LocalDateTime start, end;
        if (isEnd) {
            start = LocalDateTime.now(clock).minusHours(1);
            end = LocalDateTime.now(clock).minusMinutes(10);
        } else {
            start = LocalDateTime.now(clock);
            end = LocalDateTime.now(clock).plusHours(1);
        }
        return new Schedule(name, start, end, team, leader);
    }

    private Member member1;
    private Member member2;
    private Member member3;
    private Team team1;
    private Team team2;
    private Schedule schedule1;

    @BeforeEach
    void setUp() {
        member1 = createMember("member1");
        member2 = createMember("member2");
        member3 = createMember("member3");
        memberRepository.saveAll(List.of(member1, member2, member3));

        team1 = createTeam("team1", member1);
        team1.join(member2);
        team1.join(member3);
        team2 = createTeam("team2", member2);
        team2.join(member1);
        team2.join(member3);
        teamRepository.saveAll(List.of(team1, team2));

        schedule1 = createSchedule("schedule1", team1, member1, false);
        scheduleRepository.save(schedule1);

        scheduleMemberRepository.saveAll(List.of(
                new ScheduleMember(schedule1, member1),
                new ScheduleMember(schedule1, member2),
                new ScheduleMember(schedule1, member3)));

    }


    @Nested
    @DisplayName("수시 피드백 전송 테스트")
    class SendFrequentFeedback {

        @Test
        @DisplayName("성공 시 204")
        void test1() throws Exception {
            // given
            var sender = FeedbackMember.of(member1);
            var receiver = FeedbackMember.of(member2);
            var team = AssociatedTeam.of(team1);
            SendFrequentFeedbackRequest request = new SendFrequentFeedbackRequest(
                    receiver.getId(),
                    team.getId(),
                    FeedbackFeeling.CONSTRUCTIVE,
                    FeedbackFeeling.CONSTRUCTIVE.getObjectiveFeedbacks().subList(1, 3),
                    "테스트 내용",
                    true
            );

            // when
            assertThat(mvc.post()
                    .uri("/api/feedbacks/frequent")
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(withLoginUser(sender))
                    .content(mapper.writeValueAsString(request))
            ).hasStatus(HttpStatus.NO_CONTENT);


            var feedbacks = loadFeedbackPort.loadFeedbacks();
            assertThat(feedbacks).hasSize(1);
            var feedback = feedbacks.get(0);
            assertThat(feedback.getSender()).isEqualTo(sender);
            assertThat(feedback.getReceiver()).isEqualTo(receiver);
            assertThat(feedback.getTeam()).isEqualTo(team);
            assertThat(feedback.getFeedbackType()).isEqualTo(FeedbackType.ANONYMOUS);
            assertThat(feedback.getFeedbackFeeling()).isEqualTo(FeedbackFeeling.CONSTRUCTIVE);
            assertThat(feedback.getObjectiveFeedbacks()).containsExactlyInAnyOrderElementsOf(FeedbackFeeling.CONSTRUCTIVE.getObjectiveFeedbacks().subList(1, 3));
            assertThat(feedback.getSubjectiveFeedback()).isEqualTo("테스트 내용");
            assertThat(feedback.isLiked()).isFalse();
        }

        @Test
        @DisplayName("요청 데이터가 잘못된 경우 400")
        void test2() throws Exception {
            // given
            Member sender = member1;
            Member receiver = member2;
            Team team = team1;
            SendFrequentFeedbackRequest request = new SendFrequentFeedbackRequest(
                    receiver.getId(),
                    team.getId(),
                    FeedbackFeeling.CONSTRUCTIVE,
                    FeedbackFeeling.CONSTRUCTIVE.getObjectiveFeedbacks().subList(0, 6),
                    "테스트 내용",
                    true
            );

            // when
            assertThat(mvc.post()
                    .uri("/api/feedbacks/frequent")
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(withLoginUser(sender))
                    .content(mapper.writeValueAsString(request))
            ).hasStatus(HttpStatus.BAD_REQUEST);

            var feedbacks = loadFeedbackPort.loadFeedbacks();
            assertThat(feedbacks).isEmpty();
        }
    }


    @Nested
    @DisplayName("정기 피드백 전송 테스트")
    class SendRegularFeedback {


        @Test
        @DisplayName("성공 시 204")
        void test1() throws Exception {
            // given
            FeedbackMember sender = FeedbackMember.of(member1);
            FeedbackMember receiver = FeedbackMember.of(member2);
            AssociatedTeam team = AssociatedTeam.of(team1);
            AssociatedSchedule schedule = AssociatedSchedule.of(schedule1);
            saveRegularFeedbackRequestPort.save(createRegularFeedbackRequest(receiver, schedule, sender));
            SendRegularFeedbackRequest request = new SendRegularFeedbackRequest(
                    receiver.getId(),
                    schedule.getId(),
                    FeedbackFeeling.CONSTRUCTIVE,
                    FeedbackFeeling.CONSTRUCTIVE.getObjectiveFeedbacks().subList(1, 3),
                    "테스트 내용",
                    true
            );

            // when
            assertThat(mvc.post()
                    .uri("/api/feedbacks/regular")
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(withLoginUser(sender))
                    .content(mapper.writeValueAsString(request))
            ).hasStatus(HttpStatus.NO_CONTENT);

            var feedbacks = loadFeedbackPort.loadFeedbacks();
            assertThat(feedbacks).hasSize(1);
            Feedback feedback = feedbacks.get(0);
            assertThat(feedback.getSender()).isEqualTo(sender);
            assertThat(feedback.getReceiver()).isEqualTo(receiver);
            assertThat(feedback.getTeam()).isEqualTo(team);
            assertThat(feedback.getFeedbackType()).isEqualTo(FeedbackType.ANONYMOUS);
            assertThat(feedback.getFeedbackFeeling()).isEqualTo(FeedbackFeeling.CONSTRUCTIVE);
            assertThat(feedback.getObjectiveFeedbacks()).containsExactlyInAnyOrderElementsOf(FeedbackFeeling.CONSTRUCTIVE.getObjectiveFeedbacks().subList(1, 3));
            assertThat(feedback.getSubjectiveFeedback()).isEqualTo("테스트 내용");
            assertThat(feedback.isLiked()).isFalse();
        }

        @Test
        @DisplayName("요청 데이터가 잘못된 경우 400")
        void test2() throws Exception {
            // given
            Member sender = member1;
            Member receiver = member2;
            Team team = team1;
            SendFrequentFeedbackRequest request = new SendFrequentFeedbackRequest(
                    receiver.getId(),
                    team.getId(),
                    FeedbackFeeling.CONSTRUCTIVE,
                    FeedbackFeeling.CONSTRUCTIVE.getObjectiveFeedbacks().subList(0, 6),
                    "테스트 내용",
                    true
            );

            // when
            assertThat(mvc.post()
                    .uri("/api/feedbacks/regular")
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(withLoginUser(sender))
                    .content(mapper.writeValueAsString(request))
            ).hasStatus(HttpStatus.BAD_REQUEST);

            List<Feedback> feedbacks = loadFeedbackPort.loadFeedbacks();
            assertThat(feedbacks).isEmpty();
        }

        @Test
        @DisplayName("정기 피드백 요청이 없을 시 400")
        void test3() throws Exception {
            // given
            Member sender = member1;
            Member receiver = member2;
            Schedule schedule = schedule1;
            SendRegularFeedbackRequest body = new SendRegularFeedbackRequest(
                    receiver.getId(),
                    schedule.getId(),
                    FeedbackFeeling.CONSTRUCTIVE,
                    FeedbackFeeling.CONSTRUCTIVE.getObjectiveFeedbacks().subList(1, 3),
                    "테스트 내용",
                    true
            );

            // when
            assertThat(mvc.post()
                    .uri("/api/feedbacks/regular")
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(withLoginUser(sender))
                    .content(mapper.writeValueAsString(body))
            ).hasStatus(HttpStatus.BAD_REQUEST);


            List<Feedback> feedbacks = loadFeedbackPort.loadFeedbacks();
            assertThat(feedbacks).isEmpty();
        }
    }

    @Nested
    @DisplayName("수시 피드백 요청 테스트")
    class RequestFrequentFeedback {

        @Test
        @DisplayName("성공 시 202")
        void test1() throws Exception {
            // given
            var sender = FeedbackMember.of(member1);
            var receiver = FeedbackMember.of(member2);
            var team = AssociatedTeam.of(team1);
            String requestedContent = "내용";

            var body = new RequestFrequentFeedbackRequest(receiver.getId(), team.getId(), requestedContent);

            // when
            assertThat(mvc.post()
                    .uri("/api/feedbacks/frequent/request")
                    .session(withLoginUser(sender))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(body))
            ).hasStatus(HttpStatus.ACCEPTED);

            var request = loadFrequentFeedbackRequestPort.load(sender.getId(), team.getId(), receiver.getId()).orElseThrow();
            assertThat(request.getRequester()).isEqualTo(sender);
            assertThat(request.getReceiver()).isEqualTo(receiver);
            assertThat(request.getTeam()).isEqualTo(team);
            assertThat(request.getRequestedContent()).isEqualTo(requestedContent);
            assertThat(request.getCreatedAt()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
        }

    }

    @Nested
    @DisplayName("수시 피드백 요청 조회 테스트")
    class GetFrequentFeedbackRequest {

        @Test
        @DisplayName("성공 시 200")
        void test1() {
            // given
            Member sender1 = member1;
            Member sender2 = member3;
            Team team = team1;
            Member receiver = member2;
            requestFrequentFeedbackUseCase
                    .requestFrequentFeedback(new RequestFrequentFeedbackCommand(sender1.getId(), team.getId(), receiver.getId(), "내용 1"));
            requestFrequentFeedbackUseCase
                    .requestFrequentFeedback(new RequestFrequentFeedbackCommand(sender2.getId(), team.getId(), receiver.getId(), "내용 2"));


            // when
            assertThat(mvc.get()
                    .uri("/api/feedbacks/frequent/request")
                    .queryParam("teamId", team.getId().toString())
                    .session(withLoginUser(receiver))
                    .contentType(MediaType.APPLICATION_JSON)
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        List<FrequentFeedbackRequestResponse> requests = mapper.readValue(result, new TypeReference<>() {
                        });
                        assertThat(requests).hasSize(2);
                        assertThat(requests).extracting(FrequentFeedbackRequestResponse::requestedContent)
                                .containsExactlyInAnyOrder("내용 1", "내용 2");
                        assertThat(requests).extracting(req -> req.requester().email())
                                .containsExactlyInAnyOrder(sender1.getEmail(), sender2.getEmail());
                    });

        }
    }

    @Nested
    @DisplayName("정기 피드백 요청 조회 테스트")
    class GetRegularFeedbackRequest {

        @Test
        @DisplayName("성공 시 200")
        void test1() {
            // given
            var sender1 = FeedbackMember.of(member1);
            var sender2 = FeedbackMember.of(member3);
            var receiver = FeedbackMember.of(member2);
            var schedule = AssociatedSchedule.of(schedule1);
            saveRegularFeedbackRequestPort.save(createRegularFeedbackRequest(sender1, schedule, receiver));
            saveRegularFeedbackRequestPort.save(createRegularFeedbackRequest(sender2, schedule, receiver));

            // when
            assertThat(mvc.get()
                    .uri("/api/feedbacks/regular/request")
                    .queryParam("scheduleId", schedule.getId().toString())
                    .session(withLoginUser(receiver))
                    .contentType(MediaType.APPLICATION_JSON)
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        List<RegularFeedbackRequestResponse> requests = mapper.readValue(result, new TypeReference<>() {
                        });
                        assertThat(requests).hasSize(2);
                        assertThat(requests).extracting(req -> req.requester().id())
                                .containsExactlyInAnyOrder(sender1.getId(), sender2.getId());
                    });

        }
    }

    @Nested
    @DisplayName("피드백 좋아요 테스트")
    class LikeFeedback {

        @Test
        @DisplayName("성공 시 204")
        void test1() {
            // given
            Member sender = member1;
            Member receiver = member2;
            Feedback feedback = createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS);
            saveFeedbackPort.saveFeedback(feedback);

            // when
            assertThat(mvc.post()
                    .uri("/api/member/{memberId}/feedbacks/{feedbackId}/liked", receiver.getId(), feedback.getId().getId())
                    .session(withLoginUser(receiver))
            ).hasStatus(HttpStatus.NO_CONTENT);

            var likedFeedback = loadFeedbackPort.loadFeedback(feedback.getId()).orElseThrow();
            assertThat(likedFeedback.isLiked()).isTrue();
        }

        @Test
        @DisplayName("본인이 아닌 경우 403")
        void test2() {
            // given
            Member sender = member1;
            Member receiver = member2;
            Member notReceiver = member3;
            Feedback feedback = createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS);
            saveFeedbackPort.saveFeedback(feedback);

            // when
            assertThat(mvc.post()
                    .uri("/api/member/{memberId}/feedbacks/{feedbackId}/liked", receiver.getId(), feedback.getId().getId())
                    .session(withLoginUser(notReceiver))
            ).hasStatus(HttpStatus.FORBIDDEN);

            var likedFeedback = loadFeedbackPort.loadFeedback(feedback.getId()).orElseThrow();
            assertThat(likedFeedback.isLiked()).isFalse();
        }
    }

    @Nested
    @DisplayName("피드백 좋아요 취소 테스트")
    class UnlikeFeedback {

        @Test
        @DisplayName("성공 시 204")
        void test1() {
            // given
            Member sender = member1;
            Member receiver = member2;
            Feedback feedback = createFeedbackWithId(sender, receiver, team1, true, true);
            saveFeedbackPort.saveFeedback(feedback);

            // when
            assertThat(mvc.delete()
                    .uri("/api/member/{memberId}/feedbacks/{feedbackId}/liked", receiver.getId(), feedback.getId().getId())
                    .session(withLoginUser(receiver))
            ).hasStatus(HttpStatus.NO_CONTENT);

            var likedFeedback = loadFeedbackPort.loadFeedback(feedback.getId()).orElseThrow();
            assertThat(likedFeedback.isLiked()).isFalse();
        }

        @Test
        @DisplayName("본인이 아닌 경우 403")
        void test2() {
            // given
            Member sender = member1;
            Member receiver = member2;
            Member notReceiver = member3;
            Feedback feedback = createFeedbackWithId(sender, receiver, team1, false, true);
            saveFeedbackPort.saveFeedback(feedback);

            // when
            assertThat(mvc.delete()
                    .uri("/api/member/{memberId}/feedbacks/{feedbackId}/liked", receiver.getId(), feedback.getId().getId())
                    .session(withLoginUser(notReceiver))
            ).hasStatus(HttpStatus.FORBIDDEN);

            var likedFeedback = loadFeedbackPort.loadFeedback(feedback.getId()).orElseThrow();
            assertThat(likedFeedback.isLiked()).isTrue();
        }
    }

    @Nested
    @DisplayName("정기 피드백 건너뛰기 테스트")
    class SkipRegularFeedbackRequest {

        @Test
        @DisplayName("성공 시 204")
        void test1() {
            // given
            var receiver = FeedbackMember.of(member1);
            var sender = FeedbackMember.of(member2);
            var schedule = AssociatedSchedule.of(schedule1);
            saveRegularFeedbackRequestPort.save(createRegularFeedbackRequest(sender, schedule, receiver));

            // when
            assertThat(mvc.delete()
                    .uri("/api/feedbacks/regular/request")
                    .queryParam("scheduleId", schedule.getId().toString())
                    .session(withLoginUser(receiver))
            ).hasStatus(HttpStatus.NO_CONTENT);

            var requests = loadRegularFeedbackRequestPort.load(receiver.getId(), schedule.getId(), sender.getId());
            assertThat(requests).isEmpty();
        }
    }

    @Nested
    @DisplayName("보낸 피드백 조회 테스트")
    class GetSentFeedbacks {

        @Test
        @DisplayName("성공 시 200")
        void test1() {
            // given
            Member sender = member1;
            Member receiver = member2;
            Feedback feedback1 = createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS);
            Feedback feedback2 = createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS);
            saveFeedbackPort.saveFeedbacks(List.of(feedback1, feedback2));

            // when
            assertThat(mvc.get()
                    .uri("/api/feedbacks/sender/{senderId}", sender.getId())
                    .session(withLoginUser(sender))
            ).hasStatus(HttpStatus.OK);
        }

        @Test
        @DisplayName("팀으로 조회")
        void test2() {
            // given
            Member sender = member1;
            Member receiver = member2;
            Team team = team2;
            saveFeedbackPort.saveFeedbacks(List.of(
                    createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS),
                    createFeedbackWithId(sender, receiver, team2, FeedbackType.ANONYMOUS),
                    createFeedbackWithId(sender, receiver, team2, FeedbackType.ANONYMOUS)));

            // when & then
            assertThat(mvc.get()
                    .uri("/api/feedbacks/sender/{senderId}", sender.getId())
                    .queryParam("teamId", team.getId().toString())
                    .session(withLoginUser(sender))
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        Paged<SentFeedbackDto> requests = mapper.readValue(result, new TypeReference<>() {
                        });
                        assertThat(requests.content()).hasSize(2);
                        assertThat(requests.content()).extracting(SentFeedbackDto::teamName)
                                .containsOnly(team.getName());
                    });

        }

        @Test
        @DisplayName("도움 여부로 조회")
        void test3() {
            // given
            Member sender = member1;
            Member receiver = member2;
            saveFeedbackPort.saveFeedbacks(List.of(
                    createFeedbackWithId(sender, receiver, team1, false, true),
                    createFeedbackWithId(sender, receiver, team2, false, false),
                    createFeedbackWithId(sender, receiver, team2, false, true)));

            // when & then
            assertThat(mvc.get()
                    .uri("/api/feedbacks/sender/{senderId}", sender.getId())
                    .queryParam("filterHelpful", "true")
                    .session(withLoginUser(sender))
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        Paged<SentFeedbackDto> requests = mapper.readValue(result, new TypeReference<>() {
                        });
                        assertThat(requests.content()).extracting(SentFeedbackDto::liked)
                                .containsOnly(true);
                    });

        }

        @Test
        @DisplayName("페이지로 조회")
        void test4() {
            // given
            Member sender = member1;
            Member receiver = member2;
            for (int i = 0; i < 20; i++) {
                saveFeedbackPort.saveFeedback(createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS));
            }

            // when & then
            assertThat(mvc.get()
                    .uri("/api/feedbacks/sender/{senderId}", sender.getId())
                    .queryParam("page", "1")
                    .session(withLoginUser(sender))
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        Paged<SentFeedbackDto> requests = mapper.readValue(result, new TypeReference<>() {
                        });
                        assertThat(requests.page()).isEqualTo(1);
                        assertThat(requests.hasNext()).isEqualTo(false);
                        assertThat(requests.content()).hasSize(10);
                    });
        }

        @Test
        @DisplayName("정렬로 조회")
        void test5() {
            // given
            Member sender = member1;
            Member receiver = member2;
            for (int i = 0; i < 20; i++) {
                saveFeedbackPort.saveFeedback(createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS));
            }
            // when & then
            assertThat(mvc.get()
                    .uri("/api/feedbacks/sender/{senderId}", sender.getId())
                    .queryParam("sortOrder", "ASC")
                    .session(withLoginUser(sender))
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        Paged<SentFeedbackDto> requests = mapper.readValue(result, new TypeReference<>() {
                        });
                        assertThat(requests.content()).extracting(SentFeedbackDto::createdAt)
                                .isSortedAccordingTo(Comparator.naturalOrder());
                    });
        }

        @Test
        @DisplayName("본인이 아닌 경우 403")
        void test6() {
            // given
            Member sender = member1;
            Member notSender = member3;
            Member receiver = member2;
            Feedback feedback = createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS);
            saveFeedbackPort.saveFeedback(feedback);

            // when
            assertThat(mvc.get()
                    .uri("/api/feedbacks/sender/{senderId}", sender.getId())
                    .session(withLoginUser(notSender))
            ).hasStatus(HttpStatus.FORBIDDEN);
        }
    }

    @Nested
    @DisplayName("받은 피드백 조회 테스트")
    class GetReceivedFeedbacks {

        @Test
        @DisplayName("성공 시 200")
        void test1() {
            // given
            Member sender = member1;
            Member receiver = member2;
            Feedback feedback1 = createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS);
            Feedback feedback2 = createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS);
            saveFeedbackPort.saveFeedbacks(List.of(feedback1, feedback2));

            // when
            assertThat(mvc.get()
                    .uri("/api/feedbacks/receiver/{receiverId}", receiver.getId())
                    .session(withLoginUser(receiver))
            ).hasStatus(HttpStatus.OK);
        }

        @Test
        @DisplayName("팀으로 조회")
        void test2() {
            // given
            Member sender = member1;
            Member receiver = member2;
            Team team = team2;
            saveFeedbackPort.saveFeedbacks(List.of(
                    createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS),
                    createFeedbackWithId(sender, receiver, team2, FeedbackType.ANONYMOUS),
                    createFeedbackWithId(sender, receiver, team2, FeedbackType.ANONYMOUS)));

            // when & then
            assertThat(mvc.get()
                    .uri("/api/feedbacks/receiver/{receiverId}", receiver.getId())
                    .queryParam("teamId", team.getId().toString())
                    .session(withLoginUser(receiver))
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        Paged<ReceivedFeedbackDto> requests = mapper.readValue(result, new TypeReference<>() {
                        });
                        assertThat(requests.content()).hasSize(2);
                        assertThat(requests.content()).extracting(ReceivedFeedbackDto::teamName)
                                .containsOnly(team.getName());
                    });

        }

        @Test
        @DisplayName("도움 여부로 조회")
        void test3() {
            // given
            Member sender = member1;
            Member receiver = member2;
            saveFeedbackPort.saveFeedbacks(List.of(
                    createFeedbackWithId(sender, receiver, team1, false, true),
                    createFeedbackWithId(sender, receiver, team2, false, false),
                    createFeedbackWithId(sender, receiver, team2, false, true)));

            // when & then
            assertThat(mvc.get()
                    .uri("/api/feedbacks/receiver/{receiverId}", receiver.getId())
                    .queryParam("filterHelpful", "true")
                    .session(withLoginUser(receiver))
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        Paged<ReceivedFeedbackDto> requests = mapper.readValue(result, new TypeReference<>() {
                        });
                        assertThat(requests.content()).extracting(ReceivedFeedbackDto::liked)
                                .containsOnly(true);
                    });

        }

        @Test
        @DisplayName("페이지로 조회")
        void test4() {
            // given
            Member sender = member1;
            Member receiver = member2;
            for (int i = 0; i < 20; i++) {
                saveFeedbackPort.saveFeedback(createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS));
            }

            // when & then
            assertThat(mvc.get()
                    .uri("/api/feedbacks/receiver/{receiverId}", receiver.getId())
                    .queryParam("page", "1")
                    .session(withLoginUser(receiver))
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        Paged<ReceivedFeedbackDto> requests = mapper.readValue(result, new TypeReference<>() {
                        });
                        assertThat(requests.page()).isEqualTo(1);
                        assertThat(requests.hasNext()).isEqualTo(false);
                        assertThat(requests.content()).hasSize(10);
                    });
        }

        @Test
        @DisplayName("정렬로 조회")
        void test5() {
            // given
            Member sender = member1;
            Member receiver = member2;
            for (int i = 0; i < 20; i++) {
                saveFeedbackPort.saveFeedback(createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS));
            }
            // when & then
            assertThat(mvc.get()
                    .uri("/api/feedbacks/receiver/{receiverId}", receiver.getId())
                    .queryParam("sortOrder", "ASC")
                    .session(withLoginUser(receiver))
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        Paged<ReceivedFeedbackDto> requests = mapper.readValue(result, new TypeReference<>() {
                        });
                        assertThat(requests.content()).extracting(ReceivedFeedbackDto::createdAt)
                                .isSortedAccordingTo(Comparator.naturalOrder());
                    });
        }

        @Test
        @DisplayName("본인이 아닌 경우 403")
        void test6() {
            // given
            Member sender = member1;
            Member notReceiver = member3;
            Member receiver = member2;
            Feedback feedback = createFeedbackWithId(sender, receiver, team1, FeedbackType.ANONYMOUS);
            saveFeedbackPort.saveFeedback(feedback);

            // when
            assertThat(mvc.get()
                    .uri("/api/feedbacks/receiver/{receiverId}", receiver.getId())
                    .session(withLoginUser(notReceiver))
            ).hasStatus(HttpStatus.FORBIDDEN);
        }
    }
}