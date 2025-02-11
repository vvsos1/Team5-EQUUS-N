import NavBar2 from '../../components/NavBar2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import LargeButton from '../../components/buttons/LargeButton';
import FooterWrapper from '../../components/wrappers/FooterWrapper';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import ProgressBar from './components/ProgressBar';
import { useEffect, useState } from 'react';

export default function FeedbackSendLayout() {
  const [step, setStep] = useState(1);
  const navigate = useNavigate();
  const lastPath = useLocation().pathname.split('/').at(-1);

  useEffect(() => {
    if (step > 1) navigate(`${step}`);
    if (step > 3) navigate('/feedback/complete/?type=SEND');
  }, [step]);

  return (
    <div className='flex size-full flex-col'>
      <StickyWrapper>
        <ProgressBar
          currentStep={parseInt(lastPath) ?? 1}
          totalStep={3}
          isVisible={lastPath !== 'send'}
        />
        <NavBar2
          canPop={true}
          onClickPop={() => navigate(-1)}
          title={lastPath === 'send' && '피드백 작성하기'}
        />
      </StickyWrapper>
      <Outlet />
      <FooterWrapper>
        {lastPath === 'send' ?
          <div className='mb-4 flex w-full justify-center'>
            <button
              className='button-2 text-gray-300 underline underline-offset-4'
              onClick={() => console.log('낄낄')}
            >
              이번 피드백 건너뛰기
            </button>
          </div>
        : <LargeButton
            isOutlined={false}
            text='다음'
            onClick={() => setStep((prev) => prev + 1)}
          />
        }
      </FooterWrapper>
    </div>
  );
}
