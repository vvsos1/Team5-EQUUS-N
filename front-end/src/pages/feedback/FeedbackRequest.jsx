import { useReducer } from 'react';
import NavBar2 from '../../components/NavBar2';
import TextArea from '../../components/TextArea';

export default function FeedbackRequest({}) {
  return (
    <div className='flex size-full flex-col'>
      <NavBar2
        canPop={true}
        onClickPop={() => {
          console.log('pop click');
        }}
      />
      <h1 className='header-2 text-gray-0 my-6 whitespace-pre-line'>
        {'백현식님에게 요청할\n피드백을 작성해주세요'}
      </h1>
      <TextArea />
    </div>
  );
}
