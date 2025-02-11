import { useEffect } from 'react';
import { useMyTeams } from './api/useMainPage';
import { useTeamContext } from './TeamContext';

// 팀 관련 로직을 처리하는 커스텀 훅
export const useUser = () => {
  const { state, dispatch } = useTeamContext();

  // 팀 리스트 설정
  const setUserId = (userId) => {
    dispatch({ type: 'SET_USER_ID', payload: userId });
  };

  return {
    userId: state.userId,
    setUserId,
  };
};
