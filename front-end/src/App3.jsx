import OnboardingNotice from './pages/mypage/components/OnboardingNotice';

function App3() {
  return (
    <div className='h-dvh w-dvw bg-gray-900'>
      <div className='scrollbar-hidden mx-auto flex h-full w-full max-w-[430px] flex-col justify-center overflow-x-hidden overflow-y-auto px-5'>
        <OnboardingNotice />
      </div>
    </div>
  );
}

export default App3;
