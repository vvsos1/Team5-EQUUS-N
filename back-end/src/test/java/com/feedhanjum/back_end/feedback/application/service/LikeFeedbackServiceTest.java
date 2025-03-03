package com.feedhanjum.back_end.feedback.application.service;

import com.feedhanjum.back_end.core.event.EventPublisher;
import com.feedhanjum.back_end.core.event.Events;
import com.feedhanjum.back_end.feedback.application.port.in.command.LikeFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.in.command.UnlikeFeedbackCommand;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.LoadFeedbackPort;
import com.feedhanjum.back_end.feedback.application.port.out.feedback.SaveFeedbackPort;
import com.feedhanjum.back_end.feedback.domain.Feedback;
import com.feedhanjum.back_end.feedback.domain.FeedbackId;
import com.feedhanjum.back_end.feedback.exception.FeedbackNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeFeedbackServiceTest {
    @Mock
    LoadFeedbackPort loadFeedbackPort;
    @Mock
    SaveFeedbackPort saveFeedbackPort;

    @InjectMocks
    LikeFeedbackService likeFeedbackService;

    @Mock
    EventPublisher eventPublisher;

    @BeforeEach
    void setup() {
        Events.setPublisher(eventPublisher);
    }

    private Feedback mockFeedback() {
        Feedback feedback = mock(Feedback.class);
        when(feedback.getId()).thenReturn(new FeedbackId(1L));
        return feedback;
    }

    private Long memberId() {
        return 2L;
    }

    private void givenFeedbackWillLoad(Feedback feedback) {
        when(loadFeedbackPort.loadFeedback(feedback.getId())).thenReturn(Optional.of(feedback));
    }

    private void givenFeedbackWillNotLoad(Feedback feedback) {
        when(loadFeedbackPort.loadFeedback(feedback.getId())).thenReturn(Optional.empty());
    }

    private void verifyFeedbackWillSave(Feedback feedback) {
        verify(saveFeedbackPort).saveFeedback(feedback);
    }

    @Nested
    @DisplayName("likeFeedback 메서드 테스트")
    class LikeFeedbackTest {
        @Test
        @DisplayName("피드백 좋아요 성공")
        void test1() {
            // given
            Feedback feedback = mockFeedback();
            Long memberId = memberId();
            var command = new LikeFeedbackCommand(feedback.getId(), memberId);

            givenFeedbackWillLoad(feedback);

            // when
            likeFeedbackService.likeFeedback(command);

            // then
            verify(feedback).like(memberId);
            verifyFeedbackWillSave(feedback);
        }

        @Test
        @DisplayName("피드백 좋아요 실패 - feedback이 없을 경우")
        void test2() {
            // given
            Feedback feedback = mockFeedback();
            Long memberId = memberId();
            var command = new LikeFeedbackCommand(feedback.getId(), memberId);

            givenFeedbackWillNotLoad(feedback);

            // when & then
            assertThatThrownBy(() -> likeFeedbackService.likeFeedback(command))
                    .isInstanceOf(FeedbackNotFound.class);

        }

    }

    @Nested
    @DisplayName("unlikeFeedback 메서드 테스트")
    class UnlikeFeedbackTest {
        @Test
        @DisplayName("피드백 좋아요 취소 성공")
        void test1() {
            // given
            Feedback feedback = mockFeedback();
            Long memberId = memberId();
            var command = new UnlikeFeedbackCommand(feedback.getId(), memberId);

            givenFeedbackWillLoad(feedback);

            // when
            likeFeedbackService.unlikeFeedback(command);

            // then
            verify(feedback).unlike(memberId);
            verifyFeedbackWillSave(feedback);
        }

        @Test
        @DisplayName("피드백 좋아요 실패 - feedback이 없을 경우")
        void test2() {
            // given
            Feedback feedback = mockFeedback();
            Long memberId = memberId();
            var command = new UnlikeFeedbackCommand(feedback.getId(), memberId);

            givenFeedbackWillNotLoad(feedback);

            // when
            assertThatThrownBy(() -> likeFeedbackService.unlikeFeedback(command))
                    .isInstanceOf(FeedbackNotFound.class);

        }

    }
}