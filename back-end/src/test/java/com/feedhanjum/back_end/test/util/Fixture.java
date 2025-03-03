package com.feedhanjum.back_end.test.util;

import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.feedback.domain.FeedbackIdGenerator;
import com.feedhanjum.back_end.feedback.domain.Receiver;
import com.feedhanjum.back_end.feedback.domain.Sender;
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

    public static Sender defaultSender() {
        return new Sender(
                1L,
                "sender",
                new ProfileImage("red", "cat")
        );
    }

    public static Receiver defaultReceiver() {
        return new Receiver(
                2L,
                "receiver",
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
