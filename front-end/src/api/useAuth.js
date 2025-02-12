import { api } from './baseApi';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { showToast } from '../utility/handleToast';
import { useUser } from '../useUser';

const BASE_URL_2 = '/api/auth';

export const useSendVerifMail = () => {
  return useMutation({
    mutationFn: (data) =>
      api.post({
        url: BASE_URL_2 + '/send-signup-verification-email',
        body: data,
      }),
  });
};

export const useVerifyToken = () => {
  return useMutation({
    mutationFn: (data) =>
      api.post({ url: BASE_URL_2 + '/verify-signup-email-token', body: data }),
  });
};

export const useSignUp = () => {
  return useMutation({
    mutationFn: (data) => api.post({ url: '/api/auth/signup', body: data }),
    onSuccess: () => {},
  });
};

export const useLogin = () => {
  const navigate = useNavigate();
  const { setUserId } = useUser();
  return useMutation({
    mutationFn: (data) => api.post({ url: '/api/auth/login', body: data }),
    onSuccess: (data) => {
      const { email, message, userId } = data;
      console.log(email, message, userId);
      setUserId(userId);
      navigate('/main');
    },
    onError: (error) => {
      showToast(`로그인 실패: ${error.message}`);
    },
  });
};
