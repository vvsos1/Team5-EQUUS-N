package com.feedhanjum.back_end.feedback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feedhanjum.back_end.auth.infra.SessionConst;
import com.feedhanjum.back_end.feedback.controller.dto.request.FrequentFeedbackSendRequest;
import com.feedhanjum.back_end.feedback.controller.dto.request.RegularFeedbackSendRequest;
import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackFeeling;
import com.feedhanjum.back_end.feedback.domain.FeedbackType;
import com.feedhanjum.back_end.feedback.repository.FeedbackRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.schedule.domain.Schedule;
import com.feedhanjum.back_end.schedule.domain.ScheduleMember;
import com.feedhanjum.back_end.schedule.domain.ScheduleRole;
import com.feedhanjum.back_end.schedule.repository.ScheduleMemberRepository;
import com.feedhanjum.back_end.schedule.repository.ScheduleRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.domain.TeamMember;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test")
class FeedbackControllerTest {

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleMemberRepository scheduleMemberRepository;


    MockHttpSession withLoginUser(Member member) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.MEMBER_ID, member.getId());
        return session;
    }

    @Nested
    @DisplayName("수시 피드백 전송 테스트")
    class SendFrequentFeedback {
        private Member member1;
        private Member member2;
        private Team team1;
        @Autowired
        private FeedbackRepository feedbackRepository;

        @BeforeEach
        void setUp() {
            member1 = new Member("member1", "email1@email.com", null);
            member2 = new Member("member2", "email2@email.com", null);
            memberRepository.saveAll(List.of(member1, member2));

            team1 = new Team("team1", member1, LocalDateTime.now(), LocalDateTime.now().plusDays(1), FeedbackType.ANONYMOUS);

            teamRepository.saveAll(List.of(team1));

            TeamMember teamMember1 = new TeamMember(team1, member1);
            TeamMember teamMember3 = new TeamMember(team1, member2);
            teamMemberRepository.saveAll(List.of(teamMember1, teamMember3));

        }

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
        private Member member1;
        private Member member2;
        private Team team1;
        private Schedule schedule1;
        @Autowired
        private FeedbackRepository feedbackRepository;

        @BeforeEach
        void setUp() {
            member1 = new Member("member1", "email1@email.com", null);
            member2 = new Member("member2", "email2@email.com", null);
            memberRepository.saveAll(List.of(member1, member2));

            team1 = new Team("team1", member1, LocalDateTime.now(), LocalDateTime.now().plusDays(1), FeedbackType.ANONYMOUS);

            teamRepository.saveAll(List.of(team1));

            TeamMember teamMember1 = new TeamMember(team1, member1);
            TeamMember teamMember2 = new TeamMember(team1, member2);
            teamMemberRepository.saveAll(List.of(teamMember1, teamMember2));

            schedule1 = new Schedule("schedule1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), team1);
            scheduleRepository.save(schedule1);

            ScheduleMember scheduleMember1 = new ScheduleMember(ScheduleRole.OWNER, schedule1, member1);
            ScheduleMember scheduleMember2 = new ScheduleMember(ScheduleRole.MEMBER, schedule1, member2);
            scheduleMemberRepository.saveAll(List.of(scheduleMember1, scheduleMember2));

        }

        @Test
        @DisplayName("성공 시 204")
        void test1() throws Exception {
            // given
            Member sender = member1;
            Member receiver = member2;
            Team team = team1;
            Schedule schedule = schedule1;
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
}