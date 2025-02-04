package com.feedhanjum.back_end.feedback.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum ObjectiveFeedback {
    LOGICAL(FeedbackCategory.GOOD_HARD_SKILL, "논리적으로 말해요"),
    CREATIVE(FeedbackCategory.GOOD_HARD_SKILL, "창의적이에요"),
    FAST_WORKER(FeedbackCategory.GOOD_HARD_SKILL, "일처리가 빨라요"),
    TOOL_SAVVY(FeedbackCategory.GOOD_HARD_SKILL, "툴 활용에 능숙해요"),

    LISTENER(FeedbackCategory.GOOD_SOFT_SKILL, "다른 사람의 의견을 경청해요"),
    LEADERSHIP(FeedbackCategory.GOOD_SOFT_SKILL, "리더십이 돋보여요"),
    HELPFUL(FeedbackCategory.GOOD_SOFT_SKILL, "많은 도움을 줘요"),
    COLLABORATIVE(FeedbackCategory.GOOD_SOFT_SKILL, "협력을 잘해요"),
    DETAIL_ORIENTED(FeedbackCategory.GOOD_SOFT_SKILL, "꼼꼼해요"),

    RESPONSIBLE(FeedbackCategory.GOOD_ATTITUDE_AND_POSTURE, "책임감이 있어요"),
    PROACTIVE(FeedbackCategory.GOOD_ATTITUDE_AND_POSTURE, "적극적이에요"),
    POSITIVE(FeedbackCategory.GOOD_ATTITUDE_AND_POSTURE, "긍정적이에요"),
    DILIGENT(FeedbackCategory.GOOD_ATTITUDE_AND_POSTURE, "성실해요"),
    HARDWORKING(FeedbackCategory.GOOD_ATTITUDE_AND_POSTURE, "노력이 느껴져요"),

    LOGICAL_REQUIRED(FeedbackCategory.BAD_EFFORT, "논리적으로 말해주세요"),
    CONCISE_REQUIRED(FeedbackCategory.BAD_EFFORT, "요점 위주로 얘기해주세요"),
    DETAILED_REQUIRED(FeedbackCategory.BAD_EFFORT, "구체적으로 말해주세요"),
    THOROUGH_REQUIRED(FeedbackCategory.BAD_EFFORT, "조금 더 꼼꼼히 준비해주세요"),
    TOOL_INEXPERIENCED(FeedbackCategory.BAD_EFFORT, "툴 활용에 미숙해요"),

    LISTEN_REQUIRED(FeedbackCategory.BAD_COMMUNICATION, "다른 사람의 의견을 경청해주세요"),
    INITIATIVE_REQUIRED(FeedbackCategory.BAD_COMMUNICATION, "주도적으로 참여해주세요"),
    POLITE_REQUIRED(FeedbackCategory.BAD_COMMUNICATION, "상대방을 배려해 부드럽게 말씀해주세요"),

    RESPONSIBLE_REQUIRED(FeedbackCategory.BAD_ATTITUDE_AND_POSTURE, "책임감을 발휘해주세요"),
    PROACTIVE_REQUIRED(FeedbackCategory.BAD_ATTITUDE_AND_POSTURE, "적극적으로 참여해주세요"),
    RATIONAL_REQUIRED(FeedbackCategory.BAD_ATTITUDE_AND_POSTURE, "감정적인 표현을 지양해주세요"),
    FOCUS_REQUIRED(FeedbackCategory.BAD_ATTITUDE_AND_POSTURE, "조금 더 집중해주세요"),
    PUNCTUAL_REQUIRED(FeedbackCategory.BAD_ATTITUDE_AND_POSTURE, "시간 약속을 지켜주세요");

    private static final Map<String, ObjectiveFeedback> FEEDBACK_MAP;

    static {
        FEEDBACK_MAP = new HashMap<>();
        for (ObjectiveFeedback feedback : values()) {
            FEEDBACK_MAP.put(feedback.description, feedback);
        }
    }

    @Getter
    private final FeedbackCategory category;

    @Getter(onMethod_ = @JsonValue)
    private final String description;

    ObjectiveFeedback(FeedbackCategory category, String description) {
        this.category = category;
        this.description = description;
    }

    @JsonCreator
    public static ObjectiveFeedback fromDescription(String content) {
        ObjectiveFeedback objectiveFeedback = FEEDBACK_MAP.get(content);
        if (objectiveFeedback == null) {
            throw new IllegalArgumentException("Invalid description: " + content);
        }
        return objectiveFeedback;
    }

}
