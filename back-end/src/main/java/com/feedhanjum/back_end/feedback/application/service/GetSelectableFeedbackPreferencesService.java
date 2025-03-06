package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.GetSelectableFeedbackPreferencesUseCase;
import com.feedhanjum.back_end.member.domain.FeedbackPreference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GetSelectableFeedbackPreferencesService implements GetSelectableFeedbackPreferencesUseCase {
    @Override
    public Map<String, List<String>> getSelectableFeedbackPreferences() {
        Map<String, List<String>> feedbackPreferenceMap = new HashMap<>();
        for (FeedbackPreference feedbackPreference : FeedbackPreference.values()) {
            List<String> descriptions = feedbackPreferenceMap.computeIfAbsent(feedbackPreference.getType(), key -> new ArrayList<>());
            descriptions.add(feedbackPreference.getDescription());
        }
        return feedbackPreferenceMap;
    }
}
