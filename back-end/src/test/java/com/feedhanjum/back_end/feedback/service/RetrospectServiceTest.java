package com.feedhanjum.back_end.feedback.service;

import com.feedhanjum.back_end.feedback.domain.Retrospect;
import com.feedhanjum.back_end.feedback.repository.RetrospectRepository;
import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.member.repository.MemberRepository;
import com.feedhanjum.back_end.team.domain.Team;
import com.feedhanjum.back_end.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrospectServiceTest {
    private static final int PAGE_SIZE = 10;
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
                .isInstanceOf(EntityNotFoundException.class);

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
                .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    @DisplayName("회고 조회 성공 - 작성자와 팀명으로 조회")
    void test4() {
        // given
        int page = 1;
        Long writerId = 1L;
        Long teamId = 2L;
        Member writer = mock();
        Team team = mock();
        ArgumentCaptor<PageRequest> pageCaptor = ArgumentCaptor.captor();
        Page<Retrospect> result = mock();

        when(memberRepository.findById(writerId)).thenReturn(Optional.of(writer));
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(retrospectRepository.findByWriterAndTeam(eq(writer), eq(team), pageCaptor.capture())).thenReturn(result);

        // when
        Page<Retrospect> retrospects = retrospectService.getRetrospects(writerId, teamId, page, Sort.Direction.DESC);

        // then
        assertThat(retrospects).isEqualTo(result);

        Pageable pageable = pageCaptor.getValue();
        assertThat(pageable.getPageNumber()).isEqualTo(page);
        assertThat(pageable.getPageSize()).isEqualTo(PAGE_SIZE);
        assertThat(pageable.getSort().isSorted()).isTrue();
        assertThat(Objects.requireNonNull(pageable.getSort().getOrderFor("id")).getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    @DisplayName("회고 조회 성공 - 작성자로 조회")
    void test5() {
        // given
        int page = 2;
        Long writerId = 1L;
        Member writer = mock();
        ArgumentCaptor<PageRequest> pageCaptor = ArgumentCaptor.captor();
        Page<Retrospect> result = mock();

        when(memberRepository.findById(writerId)).thenReturn(Optional.of(writer));
        when(retrospectRepository.findByWriter(eq(writer), pageCaptor.capture())).thenReturn(result);

        // when
        Page<Retrospect> retrospects = retrospectService.getRetrospects(writerId, null, page, Sort.Direction.ASC);

        // then
        assertThat(retrospects).isEqualTo(result);

        Pageable pageable = pageCaptor.getValue();
        assertThat(pageable.getPageNumber()).isEqualTo(page);
        assertThat(pageable.getPageSize()).isEqualTo(PAGE_SIZE);
        assertThat(pageable.getSort().isSorted()).isTrue();
        assertThat(Objects.requireNonNull(pageable.getSort().getOrderFor("id")).getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    @DisplayName("회고 조회 실패 - writerId에 해당하는 Member가 없을 때")
    void test6() {
        // given
        int page = 1;
        Long writerId = 1L;

        when(memberRepository.findById(writerId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> retrospectService.getRetrospects(writerId, null, page, Sort.Direction.DESC))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("회고 조회 실패 - teamName에 해당하는 Team이 없을 때")
    void test7() {
        // given
        int page = 1;
        Long writerId = 1L;
        Long teamId = 2L;
        Member writer = mock();

        when(memberRepository.findById(writerId)).thenReturn(Optional.of(writer));
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> retrospectService.getRetrospects(writerId, teamId, page, Sort.Direction.ASC))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("회고 조회 실패 - page가 음수일 때")
    void test8() {
        // given
        int page = -1;
        Long writerId = 1L;
        Long teamId = 2L;

        // when & then
        assertThatThrownBy(() -> retrospectService.getRetrospects(writerId, teamId, page, Sort.Direction.ASC))
                .isInstanceOf(IllegalArgumentException.class);
    }
}