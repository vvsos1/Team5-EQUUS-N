package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.GetSelectableObjectiveFeedbacksUseCase;
import com.feedhanjum.back_end.feedback.domain.feedback.ObjectiveFeedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GetSelectableObjectiveFeedbacksService implements GetSelectableObjectiveFeedbacksUseCase {
    @Override
    public Map<String, Map<String, List<String>>> getSelectableObjectiveFeedbacks() {
        Map<String, Map<String, List<String>>> objectiveFeedbacksMap = new HashMap<>();
        for (ObjectiveFeedback objectiveFeedback : ObjectiveFeedback.values()) {
            Map<String, List<String>> objectiveFeedbackFeelingMap = objectiveFeedbacksMap.computeIfAbsent(objectiveFeedback.getFeeling().getDescription(), key -> new HashMap<>());
            List<String> objectiveFeedbackDescriptions = objectiveFeedbackFeelingMap.computeIfAbsent(objectiveFeedback.getCategory().getDescription(), key -> new ArrayList<>());
            objectiveFeedbackDescriptions.add(objectiveFeedback.getDescription());
        }
        return objectiveFeedbacksMap;
    }
}
