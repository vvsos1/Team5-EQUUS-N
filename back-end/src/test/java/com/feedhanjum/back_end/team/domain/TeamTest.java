package com.feedhanjum.back_end.team.domain;

import com.feedhanjum.back_end.member.domain.Member;
import com.feedhanjum.back_end.team.exception.TeamLeaderMustExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TeamTest {

    @Test
    @DisplayName("팀 리더 변경 시, null 입력으로 인한 예외 발생 확인")
    void changeLeader_예외발생() {
        // given
        Team team = new Team();

        // when, then
        assertThatThrownBy(() -> team.changeLeader(null))
                .isInstanceOf(TeamLeaderMustExistException.class)
                .hasMessage("팀 리더는 반드시 존재하는 사용자여야 합니다.");
    }

    @Test
    @DisplayName("팀 리더 변경 시, 정상 입력에 따른 리더 변경 확인")
    void changeLeader_성공() {
        // given
        Team team = new Team();
        Member member = mock(Member.class);
        when(member.getName()).thenReturn("haha");
        when(member.getEmail()).thenReturn("hoho");

        // when
        team.changeLeader(member);

        // then
        assertThat(team.getLeader()).isEqualTo(member);
    }
}