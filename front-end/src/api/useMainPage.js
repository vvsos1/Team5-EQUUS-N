import { api } from './baseApi';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';

/**
 *  팀 목록을 가져오는 훅 (쿠키에 있는 sessionId로 조회)
 */
export const useMyTeams = () => {
  return useQuery({
    queryKey: ['myTeams'],
    queryFn: () => api.get('/api/team/my-teams'),
  });
};

/**
 * 메인카드 데이터를 가져오는 훅
 * @param {number} teamId
 */
export const useMainCard = (teamId) => {
  return useQuery({
    queryKey: ['mainCard'],
    queryFn: () => api.get(`/recentSchedule/${teamId}`), // 임시
  });
};

/**
 * 메인카드2 데이터를 가져오는 훅
 * @param {number} teamId
 * @returns
 */
export const useMainCard2 = (teamId) => {
  return useQuery({
    queryKey: ['mainCard2', teamId],
    queryFn: () => api.get(`/api/team/${teamId}/members`),
  });
};

/**
 * post 예시. invalidateQueries로 myTeams 쿼리를 다시 불러옴
 */
export const usePostExample = () => {
  const queryClient = useQueryClient();
  return useMutation((data) => api.post('/api/post', data), {
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['myTeams'] });
    },
  });
};
