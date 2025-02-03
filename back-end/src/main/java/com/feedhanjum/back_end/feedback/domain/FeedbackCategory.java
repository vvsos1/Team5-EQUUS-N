package com.feedhanjum.back_end.feedback.domain;

import lombok.Getter;

@Getter
public enum FeedbackCategory {

    GOOD_HARD_SKILL(FeedbackFeeling.POSITIVE, "하드 스킬"),
    GOOD_SOFT_SKILL(FeedbackFeeling.POSITIVE, "소프트 스킬"),
    GOOD_ATTITUDE_AND_POSTURE(FeedbackFeeling.POSITIVE, "태도와 자세"),

    BAD_EFFORT(FeedbackFeeling.CONSTRUCTIVE, "노력"),
    BAD_COMMUNICATION(FeedbackFeeling.CONSTRUCTIVE, "협업 능력"),
    BAD_ATTITUDE_AND_POSTURE(FeedbackFeeling.CONSTRUCTIVE, "태도와 자세");

    private final FeedbackFeeling feeling;
    private final String description;

    FeedbackCategory(FeedbackFeeling feeling, String description) {
        this.feeling = feeling;
        this.description = description;
    }
}
