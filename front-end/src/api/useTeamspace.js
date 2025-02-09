import { useMutation, useQuery } from '@tanstack/react-query';
import { api } from './baseApi';

export const useMembers = (teamId) => {
  return useQuery({
    queryKey: ['members', teamId],
    queryFn: () => api.get({ url: `/api/team/${teamId}/members` }),
  });
};

export const useTeam = (teamId) => {
  return useQuery({
    queryKey: ['team', teamId],
    queryFn: () => api.get({ url: `/api/team/${teamId}` }),
  });
};

export const useSetLeader = (teamId) => {
  return useMutation({
    mutationFn: (leaderId) => {
      const sendingData = {
        newLeaderId: leaderId,
      };
      api.post({ url: `/api/team/${teamId}/leader`, body: sendingData });
    },
  });
};

export const useKickMember = (teamId) => {
  return useMutation({
    mutationFn: (memberId) => {
      api.delete({ url: `/api/team/${teamId}/member/${memberId}` });
    },
  });
};
