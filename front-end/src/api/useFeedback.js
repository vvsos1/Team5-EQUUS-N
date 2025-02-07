import { api } from './baseApi';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

export const useFeedbackReceived = (memberId) => {
  return useQuery({
    queryKey: ['feedback-received'],
    queryFn: () => api.get(`/api/feedbacks/receiver/${memberId}`),
  });
};

export const useFeedbackSent = (memberId) => {
  return useQuery({
    queryKey: ['feedback-sent'],
    queryFn: () => api.get(`/api/feedbacks/sender/${memberId}`),
  });
};
