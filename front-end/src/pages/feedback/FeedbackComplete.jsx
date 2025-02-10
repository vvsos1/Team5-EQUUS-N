import { useLocation, useNavigate } from 'react-router-dom';
import LargeButton from '../../components/buttons/LargeButton';
import FooterWrapper from '../../components/wrappers/FooterWrapper';

export const completeType = Object.freeze({
  REQUEST: '피드백 요청 완료!',
  SEND: '피드백 전송 완료!',
  RETROSPECT: '회고 작성 완료!',
});

export default function FeedbackComplete() {
  const location = useLocation();
  const navigate = useNavigate();

  const queryParams = new URLSearchParams(location.search);
  const typeKey = queryParams.get('type');

  return (
    <div className='flex size-full flex-col items-center justify-center'>
      <div className='size-48 rounded-full bg-lime-500' />
      <h1 className='header-2 my-6 text-gray-100'>{completeType[typeKey]}</h1>
      <FooterWrapper>
        <LargeButton
          isOutlined={false}
          text='확인'
          onClick={() => navigate('/main')}
        />
      </FooterWrapper>
    </div>
  );
}
