package com.feedhanjum.back_end.feedback.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ObjectiveFeedback {
    LOGICAL(FeedbackCategory.POSITIVE, "논리적으로 말해요"),
    CREATIVE(FeedbackCategory.POSITIVE, "창의적이에요"),
    FAST_WORKER(FeedbackCategory.POSITIVE, "일처리가 빨라요"),
    TOOL_SAVVY(FeedbackCategory.POSITIVE, "툴 활용에 능숙해요"),

    LISTENER(FeedbackCategory.POSITIVE, "다른 사람의 의견을 경청해요"),
    LEADERSHIP(FeedbackCategory.POSITIVE, "리더십이 돋보여요"),
    HELPFUL(FeedbackCategory.POSITIVE, "많은 도움을 줘요"),
    COLLABORATIVE(FeedbackCategory.POSITIVE, "협력을 잘해요"),
    DETAIL_ORIENTED(FeedbackCategory.POSITIVE, "꼼꼼해요"),

    RESPONSIBLE(FeedbackCategory.POSITIVE, "책임감이 있어요"),
    PROACTIVE(FeedbackCategory.POSITIVE, "적극적이에요"),
    POSITIVE(FeedbackCategory.POSITIVE, "긍정적이에요"),
    DILIGENT(FeedbackCategory.POSITIVE, "성실해요"),
    HARDWORKING(FeedbackCategory.POSITIVE, "노력이 느껴져요"),

    LOGICAL_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "논리적으로 말해주세요"),
    CONCISE_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "요점 위주로 얘기해주세요"),
    DETAILED_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "구체적으로 말해주세요"),
    THOROUGH_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "조금 더 꼼꼼히 준비해주세요"),
    TOOL_INEXPERIENCED(FeedbackCategory.CONSTRUCTIVE, "툴 활용에 미숙해요"),

    LISTEN_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "다른 사람의 의견을 경청해주세요"),
    INITIATIVE_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "주도적으로 참여해주세요"),
    POLITE_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "상대방을 배려해 부드럽게 말씀해주세요"),

    RESPONSIBLE_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "책임감을 발휘해주세요"),
    PROACTIVE_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "적극적으로 참여해주세요"),
    RATIONAL_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "감정적인 표현을 지양해주세요"),
    FOCUS_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "조금 더 집중해주세요"),
    PUNCTUAL_REQUIRED(FeedbackCategory.CONSTRUCTIVE, "시간 약속을 지켜주세요");

    private static final Map<String, ObjectiveFeedback> FEEDBACK_MAP;

    static {
        FEEDBACK_MAP = new HashMap<>();
        for (ObjectiveFeedback feedback : values()) {
            FEEDBACK_MAP.put(feedback.content, feedback);
        }
    }

    @Getter
    private final FeedbackCategory category;
    @Getter
    private final String content;

    ObjectiveFeedback(FeedbackCategory category, String content) {
        this.category = category;
        this.content = content;
    }


    public static Optional<ObjectiveFeedback> from(String content) {
        return Optional.ofNullable(FEEDBACK_MAP.get(content));
    }

}
