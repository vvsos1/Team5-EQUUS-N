package com.feedhanjum.back_end.feedback.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public enum FeedbackFeeling {
    POSITIVE("칭찬해요"),
    CONSTRUCTIVE("아쉬워요");

    private static final Map<FeedbackFeeling, List<ObjectiveFeedback>> OBJECTIVE_FEEDBACKS;

    static {
        OBJECTIVE_FEEDBACKS = new EnumMap<>(FeedbackFeeling.class);
        for (ObjectiveFeedback objectiveFeedback : ObjectiveFeedback.values()) {
            OBJECTIVE_FEEDBACKS
                    .computeIfAbsent(objectiveFeedback.getCategory().getFeeling(), discard -> new ArrayList<>())
                    .add(objectiveFeedback);
        }
    }

    @Getter(onMethod_ = @JsonValue)
    private final String description;

    FeedbackFeeling(String description) {
        this.description = description;
    }

    public boolean isValidObjectiveFeedback(ObjectiveFeedback objectiveFeedback) {
        return this == objectiveFeedback.getCategory().getFeeling();
    }

    // 카테고리별 허용된 객관식 피드백 목록 반환
    public List<ObjectiveFeedback> getObjectiveFeedbacks() {
        return OBJECTIVE_FEEDBACKS.get(this);
    }


    @JsonCreator
    public static FeedbackFeeling fromDescription(String description) {
        for (FeedbackFeeling feeling : values()) {
            if (feeling.description.equals(description)) {
                return feeling;
            }
        }
        throw new IllegalArgumentException("Invalid description: " + description);
    }
}
