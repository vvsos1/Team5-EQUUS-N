package com.feedhanjum.test.util;

import com.feedhanjum.feedback.domain.AssociatedTeam;
import com.feedhanjum.feedback.domain.FeedbackMember;
import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackFeeling;
import com.feedhanjum.feedback.domain.feedback.FeedbackIdGenerator;
import com.feedhanjum.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.feedback.test.SimpleFeedbackIdGenerator;
import com.feedhanjum.member.domain.FeedbackPreference;
import com.feedhanjum.member.domain.Member;
import com.feedhanjum.member.domain.ProfileImage;
import com.feedhanjum.team.domain.Team;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DomainTestUtils {
    private static final AtomicLong nextId = new AtomicLong(1);

    private static FeedbackIdGenerator feedbackIdGenerator = new SimpleFeedbackIdGenerator();

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

    public static Feedback createFeedbackWithId(Member member, Member receiver, Team team, FeedbackType feedbackType) {
        return new Feedback(
                feedbackIdGenerator.generateFeedbackId(),
                feedbackType,
                FeedbackFeeling.POSITIVE,
                FeedbackFeeling.POSITIVE.getObjectiveFeedbacks().subList(0, 3),
                "좋아요",
                false,
                FeedbackMember.of(member),
                FeedbackMember.of(receiver),
                AssociatedTeam.of(team),
                LocalDateTime.of(2022, 1, 1, 0, 0));
    }

    public static Feedback createFeedbackWithId(Member member, Member receiver, Team team, boolean isAnonymous, boolean isLiked) {
        return new Feedback(
                feedbackIdGenerator.generateFeedbackId(),
                isAnonymous ? FeedbackType.ANONYMOUS : FeedbackType.IDENTIFIED,
                FeedbackFeeling.POSITIVE,
                FeedbackFeeling.POSITIVE.getObjectiveFeedbacks().subList(0, 3),
                "좋아요",
                isLiked,
                FeedbackMember.of(member),
                FeedbackMember.of(receiver),
                AssociatedTeam.of(team),
                LocalDateTime.of(2022, 1, 1, 0, 0));
    }

}
