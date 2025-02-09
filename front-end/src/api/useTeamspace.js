import { useQuery } from '@tanstack/react-query';
import { api } from './baseApi';

export const useMembers = (teamId) => {
  return useQuery({
    queryKey: ['members', teamId],
    queryFn: () => api.get({ url: `/api/team/${teamId}/members` }),
  });
};
