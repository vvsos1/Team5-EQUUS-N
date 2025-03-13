package com.feedhanjum.feedback.application.port.in;

import java.util.List;
import java.util.Map;

public interface GetSelectableFeedbackPreferencesUseCase {

    Map<String, List<String>> getSelectableFeedbackPreferences();
}
