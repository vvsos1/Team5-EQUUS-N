import { api } from './baseApi';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { checkSignInInfos } from '../utility/inputChecker';
import { showToast } from '../utility/handleToast';

export const useVerify = () => {
  return useMutation({
    mutationFn: (data) =>
      api.post('/api/auth/send-signup-verification-email', data),
  });
};

export const useSignUp = () => {
  return useMutation({
    mutationFn: (data) => api.post('/api/auth/signup', data),
  });
};

export const useLogin = () => {
  const navigate = useNavigate();
  return useMutation({
    mutationFn: (data) => api.post('/api/auth/login', data),
    onSuccess: (data) => {
      const { email, message, userId } = data;
      console.log(email, message, userId);
      navigate('/main');
    },
    onError: (error) => {
      showToast(`로그인 실패: ${error.message}`);
    },
  });
};

export const useMyTeams = () => {
  return useQuery({
    queryKey: ['myTeams'],
    queryFn: () => api.get('/api/team/my-teams2'),
  });
};
