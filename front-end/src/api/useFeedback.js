import { api } from './baseApi';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { showToast } from '../utility/handleToast';

export const useFeedbackReceived = (userId, params) => {
  return useQuery({
    queryKey: ['feedback-received', userId, params],
    queryFn: () => {
      const queryParams = new URLSearchParams();
      if (params.teamId) queryParams.set('teamId', params.teamId);
      if (params.onlyLiked) queryParams.set('filterHelpful', params.onlyLiked);
      if (params.sortBy)
        queryParams.set(
          'sortOrder',
          params.sortBy === 'createdAt:desc' ? 'DESC' : 'ASC',
        );
      if (params.page) queryParams.set('page', params.page);
      return api.get(
        `/api/feedbacks/receiver/${userId}?${queryParams.toString()}`,
      );
    },
  });
};

export const useFeedbackSent = (userId, params) => {
  return useQuery({
    queryKey: ['feedback-sent', userId, params],
    queryFn: () => {
      const queryParams = new URLSearchParams();
      if (params.teamId) queryParams.set('teamId', params.teamId);
      if (params.onlyLiked) queryParams.set('filterHelpful', params.onlyLiked);
      if (params.sortBy)
        queryParams.set(
          'sortOrder',
          params.sortBy === 'createdAt:desc' ? 'DESC' : 'ASC',
        );
      if (params.page) queryParams.set('page', params.page);
      return api.get(
        `/api/feedbacks/sender/${userId}?${queryParams.toString()}`,
      );
    },
  });
};

export const useFeedbackLike = (userId, feedbackId) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: () =>
      api.post(`/api/member/${userId}/feedbacks/${feedbackId}/liked`),
    onSuccess: (data) => {
      showToast(data.message);
      queryClient.invalidateQueries({ queryKey: ['feedback-received'] });
    },
    onError: (error) => {
      showToast(error.response.data.message);
    },
  });
};

export const useFeedbackCancelLike = (userId, feedbackId) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: () =>
      api.delete(`/api/member/${userId}/feedbacks/${feedbackId}/liked`),
    onSuccess: (data) => {
      showToast(data.message);
      queryClient.invalidateQueries({ queryKey: ['feedback-received'] });
    },
    onError: (error) => {
      showToast(error.response.data.message);
    },
  });
};
