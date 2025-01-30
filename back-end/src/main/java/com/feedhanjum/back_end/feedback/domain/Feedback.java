package com.feedhanjum.back_end.feedback.domain;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.team.domain.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Feedback {
    private static final int MIN_OBJECTIVE_FEEDBACK_SIZE = 1;
    private static final int MAX_OBJECTIVE_FEEDBACK_SIZE = 5;
    // 객관식 피드백
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "objective_feedback", joinColumns = @JoinColumn(name = "feedback_id"))
    private final Set<ObjectiveFeedback> objectiveFeedbacks = new HashSet<>();
    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;
    @Enumerated(EnumType.STRING)
    private FeedbackCategory feedbackCategory;
    private String subjectiveFeedback;

    private boolean liked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    /**
     * @throws IllegalArgumentException 객관식 피드백이 보기에 없거나, 피드백 카테고리에 맞지 않는 값이 있을 경우, 또는 객관식 피드백이 1개 이상 5개 이하가 아닐 경우
     */
    @Builder
    public Feedback(FeedbackType feedbackType, FeedbackCategory feedbackCategory, List<String> objectiveFeedbacks, String subjectiveFeedback, Member sender, Member receiver, Team team) {
        this.feedbackType = feedbackType;
        this.subjectiveFeedback = subjectiveFeedback;
        this.feedbackCategory = feedbackCategory;
        for (String objectiveFeedback : objectiveFeedbacks) {
            this.objectiveFeedbacks.add(ObjectiveFeedback.from(objectiveFeedback)
                    .orElseThrow(() -> new IllegalArgumentException("허용되지 않는 객관식 피드백 값입니다:" + objectiveFeedback)));
        }
        this.sender = sender;
        this.team = team;
        validateObjectiveFeedbacksCategory();
        setReceiver(receiver);
    }

    public void like() {
        if (!liked) {
            this.liked = true;
        }
    }

    public void unlike() {
        if (liked) {
            this.liked = false;
        }
    }

    private void validateObjectiveFeedbacksCategory() {
        if (!(MIN_OBJECTIVE_FEEDBACK_SIZE <= objectiveFeedbacks.size()
              && objectiveFeedbacks.size() <= MAX_OBJECTIVE_FEEDBACK_SIZE)) {
            throw new IllegalArgumentException("객관식 피드백은 " + MIN_OBJECTIVE_FEEDBACK_SIZE + "개 이상 " + MAX_OBJECTIVE_FEEDBACK_SIZE + "개 이하만 가능합니다.");
        }
        for (ObjectiveFeedback objectiveFeedback : objectiveFeedbacks) {
            if (!feedbackCategory.isValidObjectiveFeedback(objectiveFeedback)) {
                throw new IllegalArgumentException("피드백 카테고리와 일치하지 않는 객관식 피드백입니다: " + objectiveFeedback);
            }
        }
    }

    private void setReceiver(Member receiver) {
        if (this.receiver != null) {
            this.receiver.getFeedbacks().remove(this);
        }
        this.receiver = receiver;
        if (receiver != null && !receiver.getFeedbacks().contains(this)) {
            receiver.getFeedbacks().add(this);
        }
    }
}
