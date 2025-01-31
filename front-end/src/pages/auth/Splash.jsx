import Lottie from 'lottie-react';
import logoLottie from '../../assets/lotties/logo_lottie.json';
import { useEffect, useState } from 'react';
import logo from '../../assets/images/logo.png';
import googleLogo from '../../assets/images/google-logo.png';
import Icon from '../../components/Icon';

export default function Splash() {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // TODO: 로그인 여부 확인
    setTimeout(() => {
      setIsLoading(false);
    }, 3000);
  }, []);

  return isLoading ?
      <div className='flex h-[20%] w-[20%] items-center justify-center'>
        <Lottie animationData={logoLottie} loop={true} autoplay={true} />
      </div>
    : <div className='rounded-400 flex h-[478px] w-full flex-col items-center justify-between bg-white px-7 py-8 transition-all duration-700 ease-in-out'>
        <div className='flex flex-col items-center gap-5'>
          <img src={logo} alt='logo' className='h-[64px] w-[64px]' />
          <h1 className='header-1 text-gray-700'>피드한줌 시작하기</h1>
          <p className='body-1 text-center'>
            피드한줌은 팀플에서 서로의 성장을 돕는 <br />
            솔직하고 간편한 피드백 교환 서비스입니다.
          </p>
        </div>
        <div className='flex w-full flex-col items-center gap-5'>
          <div className='flex w-full flex-col items-center gap-2'>
            <button className='flex w-full items-center gap-2 rounded-full bg-[#F5F4F7] px-5'>
              <img src={googleLogo} alt='google' className='ml-4' />
              <p className='mx-auto py-[15px]'>Google로 시작하기</p>
            </button>
            <button className='flex w-full items-center gap-2 rounded-full border border-[#A3A3A3] bg-white px-5'>
              <div className='ml-4'>
                <Icon name='mail' color='#595959' />
              </div>
              <p className='mx-auto py-[15px]'>이메일로 시작하기</p>
            </button>
          </div>
          <div className='flex gap-1.5'>
            <p className='caption-1 text-[#414141]'>이미 회원이신가요?</p>
            <a className='caption-2 text-[#414141]'> 로그인하기 </a>
          </div>
        </div>
      </div>;
}
