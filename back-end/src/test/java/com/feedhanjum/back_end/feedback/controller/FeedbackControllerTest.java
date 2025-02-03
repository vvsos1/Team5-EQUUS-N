package com.feedhanjum.back_end.feedback.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feedhanjum.back_end.auth.infra.SessionConst;
import com.feedhanjum.back_end.feedback.controller.dto.request.FrequentFeedbackRequestForApiRequest;
import com.feedhanjum.back_end.feedback.controller.dto.request.FrequentFeedbackRequestQueryRequest;
import com.feedhanjum.back_end.feedback.controller.dto.request.FrequentFeedbackSendRequest;
import com.feedhanjum.back_end.feedback.controller.dto.request.RegularFeedbackSendRequest;
import com.feedhanjum.back_end.feedback.controller.dto.response.FrequentFeedbackRequestDto;
import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackFeeling;
import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.feedback.repository.FeedbackRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.domain.RegularFeedbackRequest;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.domain.ScheduleRole;
import com.feedhanjum.back_end.schedule.repository.RegularFeedbackRequestRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleMemberRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.team.domain.FrequentFeedbackRequest;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.domain.TeamMember;
import com.feedhanjum.back_end.team.repository.FrequentFeedbackRequestRepository;
import com.feedhanjum.back_end.team.repository.TeamMemberRepository;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleMemberRepository scheduleMemberRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private RegularFeedbackRequestRepository regularFeedbackRequestRepository;
    @Autowired
    private FrequentFeedbackRequestRepository frequentFeedbackRequestRepository;

    private Member member1;
    private Member member2;
    private Member member3;
    private Team team1;
    private TeamMember teamMember1;
    private TeamMember teamMember2;
    private TeamMember teamMember3;
    private Schedule schedule1;
    private ScheduleMember scheduleMember1;
    private ScheduleMember scheduleMember2;
    private ScheduleMember scheduleMember3;

    @BeforeEach
    void setUp() {
        member1 = new Member("member1", "email1@email.com", new ProfileImage("bg1", "character1"));
        member2 = new Member("member2", "email2@email.com", new ProfileImage("bg2", "character2"));
        member3 = new Member("member3", "email3@email.com", new ProfileImage("bg3", "character3"));
        memberRepository.saveAll(List.of(member1, member2, member3));

        team1 = new Team("team1", member1, LocalDateTime.now(), LocalDateTime.now().plusDays(1), FeedbackType.ANONYMOUS);

        teamRepository.saveAll(List.of(team1));

        teamMember1 = new TeamMember(team1, member1);
        teamMember2 = new TeamMember(team1, member2);
        teamMember3 = new TeamMember(team1, member3);
        teamMemberRepository.saveAll(List.of(teamMember1, teamMember2, teamMember3));

        schedule1 = new Schedule("schedule1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), team1);
        scheduleRepository.save(schedule1);

        scheduleMember1 = new ScheduleMember(ScheduleRole.OWNER, schedule1, member1);
        scheduleMember2 = new ScheduleMember(ScheduleRole.MEMBER, schedule1, member2);
        scheduleMember3 = new ScheduleMember(ScheduleRole.MEMBER, schedule1, member3);
        scheduleMemberRepository.saveAll(List.of(scheduleMember1, scheduleMember2, scheduleMember3));

    }


    MockHttpSession withLoginUser(Member member) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.MEMBER_ID, member.getId());
        return session;
    }

    @Nested
    @DisplayName("수시 피드백 전송 테스트")
    class SendFrequentFeedback {

        @Test
        @DisplayName("성공 시 204")
        void test1() throws Exception {
            // given
            Member sender = member1;
            Member receiver = member2;
            Team team = team1;
            FrequentFeedbackSendRequest request = new FrequentFeedbackSendRequest(
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


            List<Feedback> feedbacks = feedbackRepository.findAll();
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
            FrequentFeedbackSendRequest request = new FrequentFeedbackSendRequest(
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

            List<Feedback> feedbacks = feedbackRepository.findAll();
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
            Member sender = member1;
            Member receiver = member2;
            Team team = team1;
            Schedule schedule = schedule1;
            regularFeedbackRequestRepository.save(
                    new RegularFeedbackRequest(LocalDateTime.now(), receiver,
                            scheduleMember1));
            RegularFeedbackSendRequest request = new RegularFeedbackSendRequest(
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


            List<Feedback> feedbacks = feedbackRepository.findAll();
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
            FrequentFeedbackSendRequest request = new FrequentFeedbackSendRequest(
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

            List<Feedback> feedbacks = feedbackRepository.findAll();
            assertThat(feedbacks).isEmpty();
        }

        @Test
        @DisplayName("수시 피드백 요청이 없을 시 400")
        void test3() throws Exception {
            // given
            Member sender = member1;
            Member receiver = member2;
            Schedule schedule = schedule1;
            RegularFeedbackSendRequest body = new RegularFeedbackSendRequest(
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


            List<Feedback> feedbacks = feedbackRepository.findAll();
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
            Member sender = member1;
            Member receiver = member2;
            Team team = team1;
            String requestedContent = "내용";

            var body = new FrequentFeedbackRequestForApiRequest(receiver.getId(), team.getId(), requestedContent);

            // when
            assertThat(mvc.post()
                    .uri("/api/feedbacks/frequent/request")
                    .session(withLoginUser(sender))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(body))
            ).hasStatus(HttpStatus.ACCEPTED);

            var requests = frequentFeedbackRequestRepository.findAll();

            assertThat(requests).hasSize(1);
            var request = requests.get(0);
            assertThat(request.getRequester()).isEqualTo(sender);
            assertThat(request.getTeamMember().getMember()).isEqualTo(receiver);
            assertThat(request.getTeamMember().getTeam()).isEqualTo(team);
            assertThat(request.getRequestedContent()).isEqualTo(requestedContent);
            assertThat(request.getCreatedAt()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
        }

    }

    @Nested
    @DisplayName("수시 피드백 요청 조회 테스트")
    class GetFrequentFeedbackRequest {

        @Test
        @DisplayName("성공 시 200")
        void test1() throws Exception {
            // given
            Member sender1 = member1;
            Member sender2 = member3;
            TeamMember teamMember = teamMember2;
            Member receiver = teamMember.getMember();
            var request = new FrequentFeedbackRequestQueryRequest(teamMember.getTeam().getId());
            frequentFeedbackRequestRepository.save(new FrequentFeedbackRequest("내용1", teamMember, sender1));
            frequentFeedbackRequestRepository.save(new FrequentFeedbackRequest("내용2", teamMember, sender2));

            // when
            assertThat(mvc.get()
                    .uri("/api/feedbacks/regular/request")
                    .session(withLoginUser(receiver))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
            ).hasStatus(HttpStatus.OK)
                    .body()
                    .satisfies(result -> {
                        List<FrequentFeedbackRequestDto> requests = mapper.readValue(result, new TypeReference<List<FrequentFeedbackRequestDto>>() {
                        });
                        assertThat(requests).hasSize(2);
                        assertThat(requests).extracting(FrequentFeedbackRequestDto::requestedContent)
                                .containsExactlyInAnyOrder("내용1", "내용2");
                        assertThat(requests).extracting(req -> req.requester().email())
                                .containsExactlyInAnyOrder(sender1.getEmail(), sender2.getEmail());
                    });

        }
    }
}