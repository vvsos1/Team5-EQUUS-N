import { api } from './baseApi';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { showToast } from '../utility/handleToast';

/**
 * 피드백 요청 커스텀 훅
 * @returns {object} - 피드백 요청 결과
 * @returns {function} - 피드백 요청 함수 로딩 여부
 *
 */
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

/**
 * 특정 유저 조회 커스텀 훅
 * @param {number} memberId
 * @returns {object} - 특정 유저 조회 결과
 */
export const useSearchMember = (memberId) => {
  return useQuery({
    queryKey: ['search-member', memberId],
    queryFn: () => {
      return api.get({
        url: `/api/member/${memberId}`,
      });
    },
  });
};
