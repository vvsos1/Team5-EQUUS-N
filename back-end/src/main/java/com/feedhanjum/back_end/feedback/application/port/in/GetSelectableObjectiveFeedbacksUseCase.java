package com.feedhanjum.back_end.feedback.application.port.in;

import java.util.List;
import java.util.Map;

public interface GetSelectableObjectiveFeedbacksUseCase {

    Map<String, Map<String, List<String>>> getSelectableObjectiveFeedbacks();
}
