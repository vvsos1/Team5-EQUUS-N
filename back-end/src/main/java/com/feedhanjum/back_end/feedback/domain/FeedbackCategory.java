package com.feedhanjum.back_end.feedback.domain;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public enum FeedbackCategory {
    POSITIVE, CONSTRUCTIVE;

    private static final Map<FeedbackCategory, List<ObjectiveFeedback>> OBJECTIVE_FEEDBACKS;

    static {
        OBJECTIVE_FEEDBACKS = new EnumMap<>(FeedbackCategory.class);
        for (ObjectiveFeedback objectiveFeedback : ObjectiveFeedback.values()) {
            OBJECTIVE_FEEDBACKS
                    .computeIfAbsent(objectiveFeedback.getCategory(), discard -> new ArrayList<>())
                    .add(objectiveFeedback);
        }
    }

    public boolean isValidObjectiveFeedback(ObjectiveFeedback objectiveFeedback) {
        return this == objectiveFeedback.getCategory();
    }

    // 카테고리별 허용된 객관식 피드백 목록 반환
    public List<ObjectiveFeedback> getObjectiveFeedbacks() {
        return OBJECTIVE_FEEDBACKS.get(this);
    }
}
