import { api } from './baseApi';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';

export const useGetSchedules = () => {
  return useQuery({
    queryKey: ['schedules'],
    queryFn: () => api.get('/api/schedules'),
  });
};

export const useGetTeamSchedules = (teamId) => {
  return useQuery({
    queryKey: ['schedules', teamId],
    queryFn: () => api.get(`/api/team/${teamId}/schedules`),
  });
};

export const useGetOneSchedule = (teamId, scheduleId) => {
  return useQuery({
    queryKey: ['schedules', teamId, scheduleId],
    queryFn: () => api.get(`/api/team/${teamId}/schedule/${scheduleId}`),
  });
};

export const usePostSchedule = (teamId) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data) => api.post(`/api/team/${teamId}/schedule/create`, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['schedules'] });
    },
  });
};

export const usePutSchedule = (teamId, scheduleId) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data) =>
      api.put(`/api/team/${teamId}/schedule/${scheduleId}`, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['schedules'] });
    },
  });
};
