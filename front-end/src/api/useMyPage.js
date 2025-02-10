import { api } from './baseApi';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { showToast } from '../utility/handleToast';

export const useGetSelfFeedback = (userId, params) => {
  return useQuery({
    queryKey: ['feedback-received', userId, params],
    queryFn: () => {
      const sendingParams = {
        teamId: params.teamId,
        sortOrder: params.sortBy === 'createdAt:desc' ? 'DESC' : 'ASC',
        page: params.page,
      };
      return api.get({
        url: `/api/retrospect/${userId}`,
        params: sendingParams,
      });
    },
  });
};
