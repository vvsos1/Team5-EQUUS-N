package com.feedhanjum.back_end.feedback.domain;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.team.domain.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Feedback {
    public static final int MIN_OBJECTIVE_FEEDBACK_SIZE = 1;
    public static final int MAX_OBJECTIVE_FEEDBACK_SIZE = 5;
    public static final int MIN_SUBJECTIVE_FEEDBACK_BYTE = 0;
    public static final int MAX_SUBJECTIVE_FEEDBACK_BYTE = 400;

    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @Enumerated(EnumType.STRING)
    private FeedbackFeeling feedbackFeeling;

    private String subjectiveFeedback;

    private boolean liked = false;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    // 객관식 피드백
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "objective_feedback", joinColumns = @JoinColumn(name = "feedback_id"))
    private final Set<ObjectiveFeedback> objectiveFeedbacks = new HashSet<>();

    /**
     * @throws IllegalArgumentException 피드백 기분에 맞지 객관식 피드백이 있을 경우, 또는 객관식 피드백이 1개 이상 5개 이하가 아닐 경우
     */
    @Builder
    public Feedback(FeedbackType feedbackType, FeedbackFeeling feedbackFeeling, List<ObjectiveFeedback> objectiveFeedbacks, String subjectiveFeedback, Member sender, Member receiver, Team team) {
        this.feedbackType = feedbackType;
        this.subjectiveFeedback = subjectiveFeedback;
        this.feedbackFeeling = feedbackFeeling;
        this.objectiveFeedbacks.addAll(objectiveFeedbacks);
        this.sender = sender;
        this.receiver = receiver;
        this.team = team;
        this.createdAt = LocalDateTime.now();
        validateObjectiveFeedbacks();
    }

    public void like(Member member) {
        if (!isReceiver(member))
            throw new SecurityException("수신자만 피드백을 좋아요 할 수 있습니다.");
        this.liked = true;

    }

    public void unlike(Member member) {
        if (!isReceiver(member))
            throw new SecurityException("수신자만 피드백 좋아요를 취소할 수 있습니다.");

        this.liked = false;
    }

    public boolean isReceiver(Member member) {
        return receiver.equals(member);
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
