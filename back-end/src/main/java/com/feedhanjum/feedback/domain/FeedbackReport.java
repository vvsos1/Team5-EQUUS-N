package com.feedhanjum.feedback.domain;

import com.feedhanjum.core.event.Events;
import com.feedhanjum.feedback.domain.feedback.Feedback;
import com.feedhanjum.feedback.domain.feedback.FeedbackCategory;
import com.feedhanjum.feedback.domain.feedback.FeedbackFeeling;
import com.feedhanjum.feedback.domain.feedback.ObjectiveFeedback;
import com.feedhanjum.feedback.event.FeedbackReportCreatedEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@Getter
public class FeedbackReport {
    public static final Integer REQUIRED_FEEDBACK_COUNT = 10;

    private final long memberId;

    private int feedbackCount;

    private final int requiredFeedbackCount;

    private final List<CategoryCount> overviews;

    private final List<KeywordCount> allKeywords;


    public FeedbackReport(Long memberId) {
        this(memberId, 0, REQUIRED_FEEDBACK_COUNT, new ArrayList<>(), new ArrayList<>());
    }

    public FeedbackReport(long memberId, int feedbackCount, int requiredFeedbackCount, List<CategoryCount> overviews, List<KeywordCount> allKeywords) {
        this.memberId = memberId;
        this.feedbackCount = feedbackCount;
        this.requiredFeedbackCount = requiredFeedbackCount;
        this.overviews = overviews;
        this.allKeywords = allKeywords;
    }

    public void applyFeedback(Feedback feedback) {
        feedbackCount += 1;
        for (ObjectiveFeedback objectiveFeedback : feedback.getObjectiveFeedbacks()) {
            applyObjectiveFeedback(objectiveFeedback);
        }
        if (feedbackCount == REQUIRED_FEEDBACK_COUNT)
            Events.raise(new FeedbackReportCreatedEvent(memberId));
    }

    public List<KeywordCount> getTopKeywords() {
        List<KeywordCount> topKeywords = new ArrayList<>();
        allKeywords.stream().filter(keywordCount -> keywordCount.feeling == FeedbackFeeling.POSITIVE).findFirst()
                .ifPresent(topKeywords::add);
        allKeywords.stream().filter(keywordCount -> keywordCount.feeling == FeedbackFeeling.CONSTRUCTIVE).findFirst()
                .ifPresent(topKeywords::add);
        return topKeywords;
    }


    public boolean isFeedbackCountEnough() {
        return feedbackCount >= requiredFeedbackCount;
    }

    private void applyObjectiveFeedback(ObjectiveFeedback objectiveFeedback) {
        FeedbackFeeling feeling = objectiveFeedback.getFeeling();
        FeedbackCategory category = objectiveFeedback.getCategory();

        OptionalInt keywordIndex = findIndex(allKeywords, keyword -> keyword.getKeyword() == objectiveFeedback);
        KeywordCount keywordCount;

        if (keywordIndex.isPresent()) {
            keywordCount = allKeywords.get(keywordIndex.getAsInt());
        } else {
            keywordCount = new KeywordCount(objectiveFeedback);
            allKeywords.add(keywordCount);
        }

        keywordCount.increaseCount();
        allKeywords.sort(KeywordCount::compareTo);

        CategoryCount categoryCount;
        OptionalInt overviewIndex = findIndex(overviews,
                overview -> overview.getCategory() == category);

        if (overviewIndex.isPresent()) {
            categoryCount = overviews.get(overviewIndex.getAsInt());
        } else {
            categoryCount = new CategoryCount(category);
            overviews.add(categoryCount);
        }

        switch (feeling) {
            case POSITIVE -> categoryCount.increaseGoodCount();
            case CONSTRUCTIVE -> categoryCount.increaseBadCount();
        }
    }

    private <T> OptionalInt findIndex(List<T> list, Predicate<T> predicate) {
        return IntStream.range(0, list.size())
                .filter(i -> predicate.test(list.get(i)))
                .findFirst();
    }

    public static FeedbackReport createNewReport(Long memberId) {
        return new FeedbackReport(memberId);
    }

    @Getter
    public static class KeywordCount implements Comparable<KeywordCount> {
        private final ObjectiveFeedback keyword;
        private final FeedbackFeeling feeling;
        // 칭찬해요는 양수, 아쉬워요는 음수값을 가짐
        private Integer count;

        public KeywordCount(ObjectiveFeedback keyword) {
            this.keyword = keyword;
            this.feeling = keyword.getFeeling();
            this.count = 0;
        }

        public void increaseCount() {
            switch (feeling) {
                case POSITIVE -> count += 1;
                case CONSTRUCTIVE -> count -= 1;
            }
        }

        @Override
        public int compareTo(KeywordCount o) {
            // count 값으로 비교
            int order = -(Math.abs(this.count) - Math.abs(o.count));
            if (order != 0) {
                return order;
            }
            // '칭찬해요' 를 '아쉬워요' 보다 더 앞으로 오도록 취급
            if (this.feeling == FeedbackFeeling.POSITIVE && o.feeling == FeedbackFeeling.CONSTRUCTIVE) {
                return -1;
            } else if (this.feeling == FeedbackFeeling.CONSTRUCTIVE && o.feeling == FeedbackFeeling.POSITIVE) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Getter
    public static class CategoryCount {
        private FeedbackCategory category;
        // 양수
        private Integer goodCount;
        // 음수
        private Integer badCount;

        public CategoryCount(FeedbackCategory category) {
            this.category = category;
            this.goodCount = 0;
            this.badCount = 0;
        }

        public void increaseGoodCount() {
            goodCount += 1;
        }

        public void increaseBadCount() {
            badCount -= 1;
        }
    }


}
