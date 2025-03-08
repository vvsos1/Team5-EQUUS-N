package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.feedback.application.port.in.feedback.LikeFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.feedback.UnlikeFeedbackUseCase;
import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.LikeFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.in.feedback.command.UnlikeFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadFeedbackPort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.domain.feedback.Feedback;
import com.feedhanjum.back_end.feedback.domain.feedback.FeedbackId;
import com.feedhanjum.back_end.feedback.exception.FeedbackNotFound;
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
