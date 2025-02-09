package com.feedhanjum.back_end.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Schema(description = "피드백 선호 정보",
        allowableValues = {"완곡한", "솔직한", "가벼운", "신중한", "간단한", "구체적인", "칭찬과 함께", "유머러스한", "현실적인", "이상적인", "논리적인", "핵심적인", "발전적인", "대안을 제시하는", "명확한", "색다른"}
)
public enum FeedbackPreference {
    EUPHEMISTIC("완곡한"),
    HONEST("솔직한"),
    LIGHT("가벼운"),
    CAUTIOUS("신중한"),
    SIMPLE("간단한"),
    DETAILED("구체적인"),
    COMPLEMENTING("칭찬과 함께"),
    HUMOROUS("유머러스한"),
    REALISTIC("현실적인"),
    IDEALISTIC("이상적인"),
    LOGICAL("논리적인"),
    ESSENTIAL("핵심적인"),
    PROGRESSIVE("발전적인"),
    ALTERNATIVE_SUGGESTING("대안을 제시하는"),
    CLEAR("명확한"),
    UNCONVENTIONAL("색다른");

    private static final Map<String, FeedbackPreference> FEEDBACK_PREFERENCE_MAP = new HashMap<>();
    private static final Set<FeedbackPreference> STYLE_PREFERENCES = Set.of(
            EUPHEMISTIC, HONEST, LIGHT, CAUTIOUS, SIMPLE, DETAILED, COMPLEMENTING, HUMOROUS
    );
    private static final Set<FeedbackPreference> CONTENT_PREFERENCES = Set.of(
            REALISTIC, IDEALISTIC, LOGICAL, ESSENTIAL, PROGRESSIVE, ALTERNATIVE_SUGGESTING, CLEAR, UNCONVENTIONAL
    );

    static {
        for (FeedbackPreference preference : values()) {
            FEEDBACK_PREFERENCE_MAP.put(preference.description, preference);
        }
    }

    private final String description;

    FeedbackPreference(String description) {
        this.description = description;
    }

    public static int countStylePreference(List<FeedbackPreference> preferences) {
        Set<FeedbackPreference> uniquePreferences = Set.copyOf(preferences);
        int count = 0;
        for (FeedbackPreference preference : uniquePreferences) {
            if (STYLE_PREFERENCES.contains(preference)) {
                count++;
            }
        }
        return count;
    }

    public static int countContentPreference(List<FeedbackPreference> preferences) {
        Set<FeedbackPreference> uniquePreferences = Set.copyOf(preferences);
        int count = 0;
        for (FeedbackPreference preference : uniquePreferences) {
            if (CONTENT_PREFERENCES.contains(preference)) {
                count++;
            }
        }
        return count;
    }

    @JsonValue
    public String getDescription(){
        return description;
    }

    @JsonCreator
    public static FeedbackPreference fromDescription(String content) {
        FeedbackPreference feedbackPreference = FEEDBACK_PREFERENCE_MAP.get(content);
        if (feedbackPreference == null) {
            throw new IllegalArgumentException("Invalid description: " + content);
        }
        return feedbackPreference;
    }
}
