package com.feedhanjum.back_end.test.util;

import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackIdGenerator;
import com.feedhanjum.back_end.feedback.test.SimpleFeedbackIdGenerator;
import com.feedhanjum.back_end.member.domain.ProfileImage;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Fixture {

    public static FeedbackIdGenerator defaultFeedbackIdGenerator() {
        return new SimpleFeedbackIdGenerator();
    }

    public static Clock defaultClock() {
        return Clock.fixed(
                LocalDateTime.of(2022, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC)
                , ZoneId.of("UTC"));
    }

    public static FeedbackMember defaultSender() {
        return new FeedbackMember(
                1L,
                "sender",
                "sender@email.com",
                new ProfileImage("red", "cat")
        );
    }

    public static FeedbackMember defaultReceiver() {
        return new FeedbackMember(
                2L,
                "receiver",
                "receiver@email.com",
                new ProfileImage("blue", "dog")
        );
    }

    public static AssociatedTeam defaultTeam() {
        return new AssociatedTeam(
                3L,
                "team"
        );
    }
}
