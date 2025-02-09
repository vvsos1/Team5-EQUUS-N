package com.feedhanjum.back_end.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;

    @Embedded
    private ProfileImage profileImage;

    @ElementCollection(targetClass = FeedbackPreference.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "feedback_preference", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "feedback_preference")
    @BatchSize(size = 16)
    private Set<FeedbackPreference> feedbackPreferences = new HashSet<>();

    public Member(String name, String email, ProfileImage profileImage, List<FeedbackPreference> feedbackPreferences) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        setFeedbackPreference(feedbackPreferences);
    }

    public void changeProfile(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeFeedbackPreference(List<FeedbackPreference> feedbackPreferences) {
        setFeedbackPreference(feedbackPreferences);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Member m)
            return getId() != null && Objects.equals(getId(), m.getId());
        return false;
    }

    private void setFeedbackPreference(List<FeedbackPreference> feedbackPreferences) {
        if (feedbackPreferences == null) {
            throw new IllegalArgumentException("피드백 선호 정보를 입력해주세요.");
        }
        int countContentPreference = FeedbackPreference.countContentPreference(feedbackPreferences);
        int countStylePreference = FeedbackPreference.countStylePreference(feedbackPreferences);
        if (countContentPreference < 1 || countContentPreference > 2) {
            throw new IllegalArgumentException("피드백 내용 선호 정보를 1개 또는 2개 넣어주세요.");
        }
        if (countStylePreference < 1 || countStylePreference > 2) {
            throw new IllegalArgumentException("피드백 스타일 선호 정보를 1개 또는 2개 넣어주세요.");
        }
        this.feedbackPreferences.clear();
        this.feedbackPreferences.addAll(feedbackPreferences);
    }
}
