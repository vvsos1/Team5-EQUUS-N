import NavBar2 from '../../components/NavBar2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import TextArea from '../../components/TextArea';
import LargeButton from '../../components/buttons/LargeButton';
import FooterWrapper from '../../components/wrappers/FooterWrapper';
import MainCard from '../main/components/MainCard';
import { useNavigate } from 'react-router-dom';
import ProgressBar from './components/ProgressBar';

export default function FeedbackSend() {
  const navigate = useNavigate();
  return (
    <div className='flex size-full flex-col'>
      <StickyWrapper>
        <ProgressBar currentStep={1} totalStep={3} />
        <NavBar2
          canPop={true}
          onClickPop={() => navigate(-1)}
          title='피드백 작성하기'
        />
        <h1 className='header-2 text-gray-0 mt-3 whitespace-pre-line'>
          {'피드백을 보낼 팀원을\n선택해 주세요.'}
        </h1>
      </StickyWrapper>

      <FooterWrapper>
        <LargeButton isOutlined={false} text='보내기' />
      </FooterWrapper>
    </div>
  );
}
