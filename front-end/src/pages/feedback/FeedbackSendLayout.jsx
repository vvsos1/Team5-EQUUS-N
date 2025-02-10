import NavBar2 from '../../components/NavBar2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import LargeButton from '../../components/buttons/LargeButton';
import FooterWrapper from '../../components/wrappers/FooterWrapper';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import ProgressBar from './components/ProgressBar';
import { useEffect, useState } from 'react';

export default function FeedbackSendLayout() {
  const [step, setStep] = useState(0);
  const navigate = useNavigate();
  const lastPath = useLocation().pathname.split('/').at(-1);

  useEffect(() => {
    if (step > 0) navigate(`${step}`);
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
        {
          <LargeButton
            isOutlined={false}
            text='다음'
            onClick={() => setStep((prev) => prev + 1)}
          />
        }
      </FooterWrapper>
    </div>
  );
}
