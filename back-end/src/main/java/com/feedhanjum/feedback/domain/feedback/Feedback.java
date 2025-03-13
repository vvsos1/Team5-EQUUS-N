package com.feedhanjum.feedback.domain.feedback;

import com.feedhanjum.core.event.Events;
import com.feedhanjum.feedback.domain.AssociatedTeam;
import com.feedhanjum.feedback.domain.FeedbackMember;
import com.feedhanjum.feedback.event.FeedbackLikedEvent;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Feedback {
    public static final int MIN_OBJECTIVE_FEEDBACK_SIZE = 1;
    public static final int MAX_OBJECTIVE_FEEDBACK_SIZE = 5;
    public static final int MIN_SUBJECTIVE_FEEDBACK_BYTE = 0;
    public static final int MAX_SUBJECTIVE_FEEDBACK_BYTE = 400;

    private final FeedbackId id;

    private final FeedbackType feedbackType;

    private final FeedbackFeeling feedbackFeeling;

    private final String subjectiveFeedback;

    private boolean liked;

    private final LocalDateTime createdAt;

    private final FeedbackMember sender;

    private final FeedbackMember receiver;

    private final AssociatedTeam team;

    private final Set<ObjectiveFeedback> objectiveFeedbacks;

    /**
     * @throws IllegalArgumentException 피드백 기분에 맞지 객관식 피드백이 있을 경우, 또는 객관식 피드백이 1개 이상 5개 이하가 아닐 경우
     */
    public Feedback(FeedbackId id, FeedbackType feedbackType, FeedbackFeeling feedbackFeeling, List<ObjectiveFeedback> objectiveFeedbacks, String subjectiveFeedback, boolean liked, FeedbackMember sender, FeedbackMember receiver, AssociatedTeam team, LocalDateTime createdAt) {
        this.id = id;
        this.feedbackType = feedbackType;
        this.subjectiveFeedback = subjectiveFeedback;
        this.feedbackFeeling = feedbackFeeling;
        this.objectiveFeedbacks = new HashSet<>(objectiveFeedbacks);
        this.liked = liked;
        this.sender = sender;
        this.receiver = receiver;
        this.team = team;
        this.createdAt = createdAt;
        validateObjectiveFeedbacks();
    }

    public void like(Long memberId) {
        if (!isReceiver(memberId))
            throw new SecurityException("수신자만 피드백을 좋아요 할 수 있습니다.");
        if (!this.liked) {
            this.liked = true;
            Events.raise(new FeedbackLikedEvent(id));
        }
    }

    public void unlike(Long memberId) {
        if (!isReceiver(memberId))
            throw new SecurityException("수신자만 피드백 좋아요를 취소할 수 있습니다.");
        this.liked = false;
    }

    private boolean isReceiver(Long memberId) {
        return receiver.getId().equals(memberId);
    }

    private void validateObjectiveFeedbacks() {
        if (!(MIN_OBJECTIVE_FEEDBACK_SIZE <= objectiveFeedbacks.size()
                && objectiveFeedbacks.size() <= MAX_OBJECTIVE_FEEDBACK_SIZE)) {
            throw new IllegalArgumentException("객관식 피드백은 " + MIN_OBJECTIVE_FEEDBACK_SIZE + "개 이상 " + MAX_OBJECTIVE_FEEDBACK_SIZE + "개 이하만 가능합니다.");
        }
        for (ObjectiveFeedback objectiveFeedback : objectiveFeedbacks) {
            if (!feedbackFeeling.isValidObjectiveFeedback(objectiveFeedback)) {
                throw new IllegalArgumentException("피드백 카테고리와 일치하지 않는 객관식 피드백입니다: " + objectiveFeedback);
            }
        }
    }

}
