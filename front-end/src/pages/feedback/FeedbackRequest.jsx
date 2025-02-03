import { useReducer } from 'react';
import NavBar2 from '../../components/NavBar2';
import TextArea from '../../components/TextArea';
import Dropdown from '../../components/Dropdown';

export default function FeedbackRequest() {
  return (
    <div className='flex size-full flex-col'>
      <NavBar2
        canPop={true}
        onClickPop={() => {
          console.log('pop click');
        }}
      />
      <Dropdown
        triggerText={'전체 보기'}
        isSmall={true}
        items={['프론트엔드', '백엔드', '데이터', '디자인', '기획']}
      />
      <Dropdown
        isSmall={false}
        items={['프론트엔드', '백엔드', '데이터', '디자인', '기획']}
      />
      <Dropdown
        isSmall={false}
        isFromTime={false}
        items={['프론트엔드', '백엔드', '데이터', '디자인', '기획']}
      />
      <h1 className='header-2 text-gray-0 my-6 whitespace-pre-line'>
        {'백현식님에게 요청할\n피드백을 작성해주세요'}
      </h1>
      <TextArea isWithGpt={true} gene={true} />
      <TextArea isWithAi={true} />
      <TextArea isWithAi={true} canToggleAnonymous={true} />
    </div>
  );
}
