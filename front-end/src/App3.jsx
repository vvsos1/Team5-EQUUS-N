import { useState } from 'react';
import FeedBackButton from './components/buttons/FeedBackButton';
import LargeButton from './components/buttons/LargeButton';
import MediumButton from './components/buttons/MediumButton';
import SmallButton from './components/buttons/SmallButton';

function App3() {
  const [currentFeedback, setCurrentFeedback] = useState(null);

  return (
    <>
      <div className='mx-5 flex h-full flex-col items-center justify-center gap-5 bg-gray-400'>
        <LargeButton
          text='큰 버튼'
          onClick={() => {}}
          isOutlined={false}
          disabled={false}
        />
        <LargeButton
          text='큰 버튼'
          onClick={() => {}}
          isOutlined={true}
          disabled={false}
        />
        <LargeButton
          text='큰 버튼'
          onClick={() => {}}
          isOutlined={false}
          disabled={true}
        />
        <LargeButton
          text='큰 버튼'
          onClick={() => {}}
          isOutlined={true}
          disabled={true}
        />
        <MediumButton
          text='중간 버튼'
          onClick={() => {}}
          isOutlined={false}
          disabled={false}
        />
        <MediumButton
          text='중간 버튼'
          onClick={() => {}}
          isOutlined={true}
          disabled={false}
        />
        <MediumButton
          text='중간 버튼'
          onClick={() => {}}
          isOutlined={false}
          disabled={true}
        />
        <MediumButton
          text='중간 버튼'
          onClick={() => {}}
          isOutlined={true}
          disabled={true}
        />
        <SmallButton
          text='작은 버튼'
          onClick={() => {}}
          isOutlined={false}
          disabled={false}
        />
        <SmallButton
          text='작은 버튼'
          onClick={() => {}}
          isOutlined={false}
          disabled={true}
        />
        <SmallButton
          text='작은 버튼'
          onClick={() => {}}
          isOutlined={true}
          disabled={false}
        />
        <SmallButton
          text='작은 버튼'
          onClick={() => {}}
          isOutlined={true}
          disabled={true}
        />
        <FeedBackButton
          currentFeedback={currentFeedback}
          onClick={setCurrentFeedback}
        />
      </div>
    </>
  );
}

export default App3;
