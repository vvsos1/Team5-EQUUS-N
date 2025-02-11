import { api } from './baseApi';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { showToast } from '../utility/handleToast';

export const useFeedbackReceived = (userId, params) => {
  return useQuery({
    queryKey: ['feedback-received', userId, params],
    queryFn: () => {
      const sendingParams = {
        teamId: params.teamId,
        filterHelpful: params.onlyLiked,
        sortOrder: params.sortBy === 'createdAt:desc' ? 'DESC' : 'ASC',
        page: params.page,
      };
      return api.get({
        url: `/api/feedbacks/receiver/${userId}`,
        params: sendingParams,
      });
    },
  });
};

export const useFeedbackSent = (userId, params) => {
  return useQuery({
    queryKey: ['feedback-sent', userId, params],
    queryFn: () => {
      const sendingParams = {
        teamId: params.teamId,
        filterHelpful: params.onlyLiked,
        sortOrder: params.sortBy === 'createdAt:desc' ? 'DESC' : 'ASC',
        page: params.page,
      };
      return api.get({
        url: `/api/feedbacks/sender/${userId}`,
        params: sendingParams,
      });
    },
  });
};

export const useFeedbackLike = (userId, feedbackId) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: () =>
      api.post({ url: `/api/member/${userId}/feedbacks/${feedbackId}/liked` }),
    onSuccess: (data) => {
      showToast(data.message);
      queryClient.invalidateQueries({ queryKey: ['feedback-received'] });
    },
    onError: (error) => {
      showToast(error.response.data.message);
    },
  });
};

export const useFeedbackLikeCancel = (userId, feedbackId) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: () =>
      api.delete({
        url: `/api/member/${userId}/feedbacks/${feedbackId}/liked`,
      }),
    onSuccess: (data) => {
      showToast(data.message);
      queryClient.invalidateQueries({ queryKey: ['feedback-received'] });
    },
    onError: (error) => {
      showToast(error.response.data.message);
    },
  });
};

export const useFeedbackFavorite = () => {
  return useQuery({
    queryKey: ['feedback-favorite'],
    queryFn: () => api.get({ url: '/api/feedbacks/favorite' }),
  });
};
