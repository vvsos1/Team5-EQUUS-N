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

  return {
    teams: state.teams,
    selectedTeam: state.selectedTeam,
    setTeams,
    selectTeam,
  };
};
