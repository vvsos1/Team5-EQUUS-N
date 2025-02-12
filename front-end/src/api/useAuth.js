import { api } from './baseApi';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { showToast } from '../utility/handleToast';
import { useUser } from '../useUser';

export const useVerify = () => {
  return useMutation({
    mutationFn: (data) =>
      api.post({ url: '/api/auth/send-signup-verification-email', body: data }),
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
