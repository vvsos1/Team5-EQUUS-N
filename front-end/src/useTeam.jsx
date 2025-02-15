import { useEffect } from 'react';
import { useMyTeams } from './api/useMainPage';
import { useTeamContext } from './TeamContext';

// 팀 관련 로직을 처리하는 커스텀 훅
export const useTeam = () => {
  const { state, dispatch } = useTeamContext();

  // 팀 리스트 설정
  const setTeams = (teams) => {
    dispatch({ type: 'SET_TEAMS', payload: teams });
  };

  // 특정 팀 선택
  const selectTeam = (teamId) => {
    if (teamId) {
      dispatch({ type: 'SELECT_TEAM', payload: teamId });
    }
  };

  // 팀 리스트 제거
  const removeTeams = () => {
    dispatch({ type: 'REMOVE_TEAMS' });
  };

  // 특정 팀 제거
  const removeSelectedTeam = () => {
    dispatch({ type: 'REMOVE_SELECTED_TEAM' });
  };

  // useTeam 호출하면 팀 목록을 가져옴
  const { data: teamsData } = useMyTeams();

  useEffect(() => {
    if (teamsData) {
      setTeams(teamsData);
    }
  }, [teamsData]);

  return {
    teams: state.teams,
    selectedTeam: state.selectedTeam,
    setTeams,
    selectTeam,
    removeTeams,
    removeSelectedTeam,
  };
};
