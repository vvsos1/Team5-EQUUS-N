package com.feedhanjum.feedback.test;

import com.feedhanjum.feedback.domain.AssociatedSchedule;
import com.feedhanjum.feedback.domain.AssociatedTeam;
import com.feedhanjum.feedback.domain.FeedbackMember;
import com.feedhanjum.feedback.domain.RegularFeedbackRequest;
import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackFeeling;
import com.feedhanjum.feedback.domain.feedback.FeedbackIdGenerator;
import com.feedhanjum.feedback.domain.feedback.FeedbackType;
import com.feedhanjum.member.domain.ProfileImage;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicLong;

public class FeedbackFixture {

    public static FeedbackIdGenerator defaultFeedbackIdGenerator() {
        return new FeedbackIdGenerator();
    }

    private static final FeedbackIdGenerator feedbackIdGenerator = defaultFeedbackIdGenerator();
    private static final AtomicLong nextMemberId = new AtomicLong(1);
    private static final AtomicLong nextTeamId = new AtomicLong(1000);

    public static Clock defaultClock() {
        return Clock.fixed(
                LocalDateTime.of(2022, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC)
                , ZoneId.of("UTC"));
    }

    public static FeedbackMember createMember(String name) {
        return new FeedbackMember(
                nextMemberId.getAndIncrement(),
                name,
                name + "@email.com",
                new ProfileImage("red", "cat")
        );
    }

    public static FeedbackMember defaultSender() {
        return createMember("sender");
    }

    public static FeedbackMember defaultReceiver() {
        return createMember("receiver");
    }

    public static AssociatedTeam defaultTeam() {
        return createTeam("team");
    }

    public static AssociatedTeam createTeam(String name) {
        return new AssociatedTeam(
                nextTeamId.getAndIncrement(),
                name
        );
    }

    public static AssociatedSchedule defaultSchedule(Long teamId) {
        LocalDateTime endTime = LocalDateTime.now(defaultClock()).minusHours(1);
        return new AssociatedSchedule(
                200L,
                "schedule",
                endTime,
                teamId
        );
    }

    public static RegularFeedbackRequest createRegularFeedbackRequest(FeedbackMember requester, AssociatedSchedule schedule, FeedbackMember receiver) {
        LocalDateTime fixed = LocalDateTime.now(defaultClock());
        return new RegularFeedbackRequest(fixed, requester, schedule, receiver);
    }

    public static Feedback createFeedback(FeedbackMember sender, FeedbackMember receiver, AssociatedTeam team) {
        return createFeedback(sender, receiver, team, false);
    }

    public static Feedback createFeedback(FeedbackMember sender, FeedbackMember receiver, AssociatedTeam team, boolean liked) {
        return new Feedback(
                feedbackIdGenerator.generateFeedbackId(),
                FeedbackType.ANONYMOUS,
                FeedbackFeeling.POSITIVE,
                FeedbackFeeling.POSITIVE.getObjectiveFeedbacks().subList(0, 3),
                "좋아요",
                liked,
                sender,
                receiver,
                team,
                LocalDateTime.of(2022, 1, 1, 0, 0)
        );
    }
}
