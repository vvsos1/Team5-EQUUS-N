import { useMutation, useQuery } from '@tanstack/react-query';
import { api } from './baseApi';

export const useMembers = (teamId) => {
  return useQuery({
    queryKey: ['members', teamId],
    queryFn: () => api.get({ url: `/api/team/${teamId}/members` }),
  });
};

export const useTeamInfo = (teamId) => {
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
      return api.post({ url: `/api/team/${teamId}/leader`, body: sendingData });
    },
  });
};

export const useKickMember = (teamId) => {
  return useMutation({
    mutationFn: (memberId) => {
      return api.delete({ url: `/api/team/${teamId}/member/${memberId}` });
    },
  });
};

export const useMakeTeam = () => {
  return useMutation({
    mutationFn: (teamInfo) => {
      const sendingData = teamInfo;
      return api.post({ url: `/api/team/create`, body: sendingData });
    },
    onSuccess: (data) => {
      return data;
    },
  });
};

export const useEditTeam = (teamId) => {
  return useMutation({
    mutationFn: (teamInfo) => {
      const sendingData = teamInfo;
      return api.post({ url: `/api/team/${teamId}/`, body: sendingData });
    },
  });
};

export const useDeleteTeam = (teamId) => {
  return useMutation({
    mutationFn: () => {
      return api.delete({ url: `/api/team/${teamId}/leave` });
    },
  });
};

export const useInviteTeam = () => {
  return useMutation({
    mutationFn: (teamId) => {
      return api.post({ url: `/api/team/${teamId}/join-token` });
    },
    onSuccess: (data) => data,
  });
};
