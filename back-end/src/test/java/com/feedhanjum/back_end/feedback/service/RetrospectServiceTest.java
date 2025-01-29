package com.feedhanjum.back_end.feedback.service;

import com.feedhanjum.back_end.feedback.domain.Retrospect;
import com.feedhanjum.back_end.feedback.repository.RetrospectRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrospectServiceTest {
    @Mock
    private RetrospectRepository retrospectRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private RetrospectService retrospectService;


    @Test
    @DisplayName("회고 작성 성공")
    void test1() {
        // given
        String content = "content";
        Long writerId = 1L;
        Long teamId = 1L;

        Member writer = mock();
        Team team = mock();

        when(memberRepository.findById(writerId)).thenReturn(Optional.of(writer));
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        // when
        Retrospect result = retrospectService.writeRetrospect(content, writerId, teamId);

        // then
        verify(retrospectRepository, times(1)).save(any());
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getWriter()).isEqualTo(writer);
        assertThat(result.getTeam()).isEqualTo(team);
    }

    @Test
    @DisplayName("회고 작성 실패 - writerId에 해당하는 Member가 없을 때")
    void test2() {
        // given
        String content = "content";
        Long writerId = 1L;
        Long teamId = 1L;

        when(memberRepository.findById(writerId)).thenReturn(Optional.empty());
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(mock()));

        // when & then
        assertThatThrownBy(() -> retrospectService.writeRetrospect(content, writerId, teamId))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    @DisplayName("회고 작성 실패 - teamId에 해당하는 Team이 없을 때")
    void test3() {
        // given
        String content = "content";
        Long writerId = 1L;
        Long teamId = 1L;

        when(memberRepository.findById(writerId)).thenReturn(Optional.of(mock()));
        when(teamRepository.findById(writerId)).thenReturn(Optional.empty());


        // when & then
        assertThatThrownBy(() -> retrospectService.writeRetrospect(content, writerId, teamId))
                .isInstanceOf(IllegalArgumentException.class);

    }
}