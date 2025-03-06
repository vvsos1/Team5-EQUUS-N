package com.feedhanjum.back_end.feedback.adapter.out.persistence;

import com.feedhanjum.back_end.feedback.domain.AssociatedTeam;
import com.feedhanjum.back_end.feedback.domain.FeedbackMember;
import com.feedhanjum.back_end.feedback.dto.ReceivedFeedbackDto;
import com.feedhanjum.back_end.feedback.dto.SentFeedbackDto;
import com.feedhanjum.back_end.feedback.test.FeedbackFixture;
import com.feedhanjum.back_end.test.annotation.PersistenceAdapterTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;

import java.util.Comparator;

import static com.feedhanjum.back_end.feedback.test.FeedbackFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import(FeedbackQuerydslAdapter.class)
class FeedbackQuerydslAdapterTest {
    @Autowired
    FeedbackQuerydslAdapter feedbackQuerydslAdapter;

    @Autowired
    FeedbackJpaEntityRepository feedbackRepository;

    private FeedbackJpaEntity createFeedback(FeedbackMember sender, FeedbackMember receiver, AssociatedTeam team, boolean liked) {
        return FeedbackJpaEntity.fromDomain(FeedbackFixture.createFeedback(sender, receiver, team, liked));
    }

    @Nested
    @DisplayName("findReceivedFeedbacks 메소드 테스트")
    class FindReceivedFeedbacks {

        private FeedbackMember sender;
        private FeedbackMember receiver;
        private AssociatedTeam team1;
        private AssociatedTeam team2;

        @BeforeEach
        void setUp() {
            sender = defaultSender();
            receiver = defaultReceiver();

            team1 = createTeam("team1");
            team2 = createTeam("team2");
        }

        @Test
        @DisplayName("모든 피드백 최신순 조회 성공")
        void test1() {
            // given
            for (int i = 0; i < 5; i++) {
                boolean liked = i % 2 == 0;
                feedbackRepository.save(createFeedback(sender, receiver, team1, liked));
                feedbackRepository.save(createFeedback(sender, receiver, team2, liked));
            }

            for (int i = 0; i < 5; i++) {
                feedbackRepository.save(createFeedback(receiver, sender, team1, false));
            }

            int page = 0;
            int pageSize = 4;
            Sort.Direction order = Sort.Direction.DESC;

            // when
            var result = feedbackQuerydslAdapter.loadReceivedFeedback(receiver.getId(), null, false, page, pageSize, order);

            // then
            assertThat(result.getContent()).hasSize(pageSize);
            assertThat(result.hasNext()).isTrue();
            assertThat(result.getNumber()).isEqualTo(page);
            assertThat(result.getTotalElements()).isEqualTo(10);
            var feedbacks = result.getContent();
            assertThat(feedbacks).hasSize(pageSize);
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::feedbackId).isSortedAccordingTo(Comparator.reverseOrder());
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::teamName).containsExactly("team2", "team1", "team2", "team1");
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::liked).containsExactly(true, true, false, false);
        }

        @Test
        @DisplayName("팀 id로 필터링 조회 성공")
        void test2() {
            // given
            for (int i = 0; i < 5; i++) {
                boolean liked = i % 2 == 0;
                feedbackRepository.save(createFeedback(sender, receiver, team1, liked));
                feedbackRepository.save(createFeedback(sender, receiver, team2, liked));
            }

            for (int i = 0; i < 5; i++) {
                feedbackRepository.save(createFeedback(receiver, sender, team1, false));
            }

            int page = 0;
            int pageSize = 4;
            Sort.Direction order = Sort.Direction.DESC;

            // when
            var result = feedbackQuerydslAdapter.loadReceivedFeedback(receiver.getId(), team1.getId(), false, page, pageSize, order);

            // then
            assertThat(result.getContent()).hasSize(pageSize);

            assertThat(result.hasNext()).isTrue();
            assertThat(result.getNumber()).isEqualTo(page);
            assertThat(result.getTotalElements()).isEqualTo(5);
            var feedbacks = result.getContent();
            assertThat(feedbacks).hasSize(pageSize);
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::feedbackId).isSortedAccordingTo(Comparator.reverseOrder());
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::teamName).containsOnly("team1");
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::liked).containsExactly(true, false, true, false);
        }

        @Test
        @DisplayName("좋아요 여부로 필터링 조회 성공")
        void test3() {
            // given
            feedbackRepository.save(createFeedback(sender, receiver, team1, true));
            feedbackRepository.save(createFeedback(sender, receiver, team2, true));
            feedbackRepository.save(createFeedback(sender, receiver, team1, false));
            feedbackRepository.save(createFeedback(sender, receiver, team1, false));
            feedbackRepository.save(createFeedback(sender, receiver, team2, true));
            feedbackRepository.save(createFeedback(sender, receiver, team2, true));

            for (int i = 0; i < 5; i++) {
                feedbackRepository.save(createFeedback(receiver, sender, team1, false));
            }

            int page = 0;
            int pageSize = 3;
            Sort.Direction order = Sort.Direction.ASC;

            // when
            var result = feedbackQuerydslAdapter.loadReceivedFeedback(receiver.getId(), null, true, page, pageSize, order);

            // then
            assertThat(result.getContent()).hasSize(pageSize);
            assertThat(result.hasNext()).isTrue();
            assertThat(result.getNumber()).isEqualTo(page);
            assertThat(result.getTotalElements()).isEqualTo(4);
            var feedbacks = result.getContent();
            assertThat(feedbacks).hasSize(pageSize);
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::feedbackId).isSortedAccordingTo(Comparator.naturalOrder());
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::teamName).containsExactly("team1", "team2", "team2");
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::liked).containsOnly(true);
        }

        @Test
        @DisplayName("과거순 정렬 조회 성공")
        void test4() {
            // given
            for (int i = 0; i < 5; i++) {
                boolean liked = i % 2 == 0;
                feedbackRepository.save(createFeedback(sender, receiver, team1, liked));
                feedbackRepository.save(createFeedback(sender, receiver, team2, liked));
            }

            for (int i = 0; i < 5; i++) {
                feedbackRepository.save(createFeedback(receiver, sender, team1, false));
            }

            int page = 0;
            int pageSize = 4;
            Sort.Direction order = Sort.Direction.ASC;

            // when
            var result = feedbackQuerydslAdapter.loadReceivedFeedback(receiver.getId(), null, false, page, pageSize, order);

            // then
            assertThat(result.getContent()).hasSize(pageSize);
            assertThat(result.hasNext()).isTrue();
            assertThat(result.getNumber()).isEqualTo(page);
            assertThat(result.getTotalElements()).isEqualTo(10);
            var feedbacks = result.getContent();
            assertThat(feedbacks).hasSize(pageSize);
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::feedbackId).isSortedAccordingTo(Comparator.naturalOrder());
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::teamName).containsExactly("team1", "team2", "team1", "team2");
            assertThat(feedbacks).extracting(ReceivedFeedbackDto::liked).containsExactly(true, true, false, false);
        }

    }


    @Nested
    @DisplayName("findSentFeedbacks 메소드 테스트")
    class FindSentFeedbacks {

        private FeedbackMember sender;
        private FeedbackMember receiver;
        private AssociatedTeam team1;
        private AssociatedTeam team2;

        @BeforeEach
        void setUp() {
            sender = defaultSender();
            receiver = defaultReceiver();

            team1 = createTeam("team1");
            team2 = createTeam("team2");
        }

        @Test
        @DisplayName("모든 피드백 최신순 조회 성공")
        void test1() {
            // given
            for (int i = 0; i < 5; i++) {
                boolean liked = i % 2 == 0;
                feedbackRepository.save(createFeedback(sender, receiver, team1, liked));
                feedbackRepository.save(createFeedback(sender, receiver, team2, liked));
            }

            for (int i = 0; i < 5; i++) {
                feedbackRepository.save(createFeedback(receiver, sender, team1, false));
            }

            int page = 0;
            int pageSize = 4;
            Sort.Direction order = Sort.Direction.DESC;

            // when
            var result = feedbackQuerydslAdapter.loadSentFeedback(sender.getId(), null, false, page, pageSize, order);

            // then
            assertThat(result.getContent()).hasSize(pageSize);
            assertThat(result.hasNext()).isTrue();
            assertThat(result.getNumber()).isEqualTo(page);
            assertThat(result.getTotalElements()).isEqualTo(10);
            var feedbacks = result.getContent();
            assertThat(feedbacks).hasSize(pageSize);
            assertThat(feedbacks).extracting(SentFeedbackDto::feedbackId).isSortedAccordingTo(Comparator.reverseOrder());
            assertThat(feedbacks).extracting(SentFeedbackDto::teamName).containsExactly("team2", "team1", "team2", "team1");
            assertThat(feedbacks).extracting(SentFeedbackDto::liked).containsExactly(true, true, false, false);
        }

        @Test
        @DisplayName("팀 id로 필터링 조회 성공")
        void test2() {
            // given
            for (int i = 0; i < 5; i++) {
                boolean liked = i % 2 == 0;
                feedbackRepository.save(createFeedback(sender, receiver, team1, liked));
                feedbackRepository.save(createFeedback(sender, receiver, team2, liked));
            }

            for (int i = 0; i < 5; i++) {
                feedbackRepository.save(createFeedback(receiver, sender, team1, false));
            }

            int page = 0;
            int pageSize = 4;
            Sort.Direction order = Sort.Direction.DESC;

            // when
            var result = feedbackQuerydslAdapter.loadSentFeedback(sender.getId(), team1.getId(), false, page, pageSize, order);

            // then
            assertThat(result.getContent()).hasSize(pageSize);

            assertThat(result.hasNext()).isTrue();
            assertThat(result.getNumber()).isEqualTo(page);
            assertThat(result.getTotalElements()).isEqualTo(5);
            var feedbacks = result.getContent();
            assertThat(feedbacks).hasSize(pageSize);
            assertThat(feedbacks).extracting(SentFeedbackDto::feedbackId).isSortedAccordingTo(Comparator.reverseOrder());
            assertThat(feedbacks).extracting(SentFeedbackDto::teamName).containsOnly("team1");
            assertThat(feedbacks).extracting(SentFeedbackDto::liked).containsExactly(true, false, true, false);
        }

        @Test
        @DisplayName("좋아요 여부로 필터링 조회 성공")
        void test3() {
            // given
            feedbackRepository.save(createFeedback(sender, receiver, team1, true));
            feedbackRepository.save(createFeedback(sender, receiver, team2, true));
            feedbackRepository.save(createFeedback(sender, receiver, team1, false));
            feedbackRepository.save(createFeedback(sender, receiver, team1, false));
            feedbackRepository.save(createFeedback(sender, receiver, team2, true));
            feedbackRepository.save(createFeedback(sender, receiver, team2, true));


            for (int i = 0; i < 5; i++) {
                feedbackRepository.save(createFeedback(receiver, sender, team1, false));
            }

            int page = 0;
            int pageSize = 3;
            Sort.Direction order = Sort.Direction.ASC;

            // when
            var result = feedbackQuerydslAdapter.loadSentFeedback(sender.getId(), null, true, page, pageSize, order);

            // then
            assertThat(result.getContent()).hasSize(pageSize);
            assertThat(result.hasNext()).isTrue();
            assertThat(result.getNumber()).isEqualTo(page);
            assertThat(result.getTotalElements()).isEqualTo(4);
            var feedbacks = result.getContent();
            assertThat(feedbacks).hasSize(pageSize);
            assertThat(feedbacks).extracting(SentFeedbackDto::feedbackId).isSortedAccordingTo(Comparator.naturalOrder());
            assertThat(feedbacks).extracting(SentFeedbackDto::teamName).containsExactly("team1", "team2", "team2");
            assertThat(feedbacks).extracting(SentFeedbackDto::liked).containsOnly(true);
        }

        @Test
        @DisplayName("과거순 정렬 조회 성공")
        void test4() {
            // given
            for (int i = 0; i < 5; i++) {
                boolean liked = i % 2 == 0;
                feedbackRepository.save(createFeedback(sender, receiver, team1, liked));
                feedbackRepository.save(createFeedback(sender, receiver, team2, liked));
            }

            for (int i = 0; i < 5; i++) {
                feedbackRepository.save(createFeedback(receiver, sender, team1, false));
            }

            int page = 0;
            int pageSize = 4;
            Sort.Direction order = Sort.Direction.ASC;

            // when
            var result = feedbackQuerydslAdapter.loadSentFeedback(sender.getId(), null, false, page, pageSize, order);

            // then
            assertThat(result.getContent()).hasSize(pageSize);
            assertThat(result.hasNext()).isTrue();
            assertThat(result.getNumber()).isEqualTo(page);
            assertThat(result.getTotalElements()).isEqualTo(10);
            var feedbacks = result.getContent();
            assertThat(feedbacks).hasSize(pageSize);
            assertThat(feedbacks).extracting(SentFeedbackDto::feedbackId).isSortedAccordingTo(Comparator.naturalOrder());
            assertThat(feedbacks).extracting(SentFeedbackDto::teamName).containsExactly("team1", "team2", "team1", "team2");
            assertThat(feedbacks).extracting(SentFeedbackDto::liked).containsExactly(true, true, false, false);
        }

    }
}