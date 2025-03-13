package com.feedhanjum.feedback.application.service;

import com.feedhanjum.feedback.application.port.in.feedback.LikeFeedbackUseCase;
import com.feedhanjum.feedback.application.port.in.feedback.UnlikeFeedbackUseCase;
import com.feedhanjum.feedback.application.port.in.feedback.command.LikeFeedbackCommand;
import com.feedhanjum.feedback.application.port.in.feedback.command.UnlikeFeedbackCommand;
import com.feedhanjum.feedback.application.port.out.feedback.LoadFeedbackPort;
import com.feedhanjum.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackId;
import com.feedhanjum.feedback.exception.FeedbackNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
class LikeFeedbackService implements LikeFeedbackUseCase, UnlikeFeedbackUseCase {
    private final LoadFeedbackPort loadFeedbackPort;
    private final SaveFeedbackPort saveFeedbackPort;

    @Transactional
    @Override
    public void likeFeedback(LikeFeedbackCommand command) {
        FeedbackId feedbackId = command.getFeedbackId();
        Feedback feedback = loadFeedbackPort.loadFeedback(feedbackId).orElseThrow(() -> new FeedbackNotFound(feedbackId));
        feedback.like(command.getMemberId());
        saveFeedbackPort.saveFeedback(feedback);
    }

    @Transactional
    @Override
    public void unlikeFeedback(UnlikeFeedbackCommand command) {
        FeedbackId feedbackId = command.getFeedbackId();
        Feedback feedback = loadFeedbackPort.loadFeedback(feedbackId).orElseThrow(() -> new FeedbackNotFound(feedbackId));
        feedback.unlike(command.getMemberId());
        saveFeedbackPort.saveFeedback(feedback);
    }
}
