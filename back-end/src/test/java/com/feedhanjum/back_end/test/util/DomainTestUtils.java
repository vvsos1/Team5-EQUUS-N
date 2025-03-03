package com.feedhanjum.back_end.test.util;

import com.feedhanjum.back_end.feedback.domain.*;
import com.feedhanjum.back_end.feedback.infra.FeedbackTSIDGenerator;
import com.feedhanjum.back_end.member.domain.FeedbackPreference;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import com.feedhanjum.back_end.team.domain.Team;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

public class DomainTestUtils {
    private static final AtomicLong nextId = new AtomicLong(1);

    private static FeedbackIdGenerator feedbackIdGenerator = new FeedbackTSIDGenerator();

    public static Member createMemberWithoutId(String name) {
        List<FeedbackPreference> feedbackPreferences = List.of(FeedbackPreference.PROGRESSIVE, FeedbackPreference.COMPLEMENTING);
        return new Member(name, name + "email@com", new ProfileImage("red", "default.png"), feedbackPreferences);
    }

    public static Member createMemberWithId(String name) {
        Long id = nextId.getAndIncrement();
        Member member = createMemberWithoutId(name);
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }

    public static Team createTeamWithoutId(String name, Member leader) {
        return createTeamWithoutId(name, leader, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), LocalDate.now());
    }

    public static Team createTeamWithoutId(String name, Member leader, LocalDate startDate, LocalDate endDate, LocalDate now) {
        return new Team(name, leader, startDate, endDate, FeedbackType.ANONYMOUS, now);
    }

    public static Team createTeamWithId(String name, Member leader, LocalDate startDate, LocalDate endDate, LocalDate now) {
        Long id = nextId.getAndIncrement();
        Team team = createTeamWithoutId(name, leader, startDate, endDate, now);
        ReflectionTestUtils.setField(team, "id", id);
        return team;
    }

    public static Team createTeamWithId(String name, Member leader) {
        return createTeamWithId(name, leader, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), LocalDate.now());
    }

    public static Feedback createFeedbackWithId(Member sender, Member receiver, Team team, FeedbackType feedbackType) {
        return new Feedback(
                feedbackIdGenerator.generateFeedbackId(),
                feedbackType,
                FeedbackFeeling.POSITIVE,
                FeedbackFeeling.POSITIVE.getObjectiveFeedbacks().subList(0, 3),
                "좋아요",
                false,
                Sender.of(sender),
                Receiver.of(receiver),
                AssociatedTeam.of(team),
                LocalDateTime.of(2022, 1, 1, 0, 0));
    }

    public static Feedback createFeedbackWithId(Member sender, Member receiver, Team team, boolean isAnonymous, boolean isLiked) {
        return new Feedback(
                feedbackIdGenerator.generateFeedbackId(),
                isAnonymous ? FeedbackType.ANONYMOUS : FeedbackType.IDENTIFIED,
                FeedbackFeeling.POSITIVE,
                FeedbackFeeling.POSITIVE.getObjectiveFeedbacks().subList(0, 3),
                "좋아요",
                isLiked,
                Sender.of(sender),
                Receiver.of(receiver),
                AssociatedTeam.of(team),
                LocalDateTime.of(2022, 1, 1, 0, 0));
    }


    public static void assertEqualSender(Member member, Sender sender) {
        assertThat(member.getId()).isEqualTo(sender.getId());
        assertThat(member.getName()).isEqualTo(sender.getName());
        assertThat(member.getProfileImage()).isEqualTo(sender.getProfileImage());
    }

    public static void assertEqualReceiver(Member member, Receiver receiver) {
        assertThat(member.getId()).isEqualTo(receiver.getId());
        assertThat(member.getName()).isEqualTo(receiver.getName());
        assertThat(member.getProfileImage()).isEqualTo(receiver.getProfileImage());
    }

    public static void assertEqualTeam(Team team, AssociatedTeam associatedTeam) {
        assertThat(team.getId()).isEqualTo(associatedTeam.getId());
        assertThat(team.getName()).isEqualTo(associatedTeam.getName());
    }
}
