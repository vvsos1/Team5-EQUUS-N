package com.feedhanjum.back_end.feedback.test;

import com.feedhanjum.back_end.feedback.domain.AssociatedSchedule;
import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.RegularFeedbackRequest;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackIdGenerator;
import com.feedhanjum.back_end.member.domain.ProfileImage;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicLong;

public class FeedbackFixture {

    public static FeedbackIdGenerator defaultFeedbackIdGenerator() {
        return new SimpleFeedbackIdGenerator();
    }

    private static final AtomicLong nextMemberId = new AtomicLong(1);

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
        return new AssociatedTeam(
                100L,
                "team"
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
}
