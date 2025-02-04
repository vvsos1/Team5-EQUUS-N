import { useReducer, useState } from 'react';
import Icon from './Icon';

export default function TextArea({
  isWithGpt = false,
  canToggleAnonymous = false,
  generatedByGpt = false,
}) {
  const [textLength, setTextLength] = useState(0);
  const [isAnonymous, toggleAnonymous] = useReducer((state) => !state, false);

  const onInput = (e) => {
    e.target.style.height = 'auto';
    e.target.style.height = `${e.target.scrollHeight}px`;
    setTextLength(e.target.value.length);
  };

  return (
    <div className='relative'>
      <textarea
        onInput={onInput}
        className={`text-gray-0 placeholder:body-1 rounded-300 relative min-h-44 w-full resize-none p-5 pb-14 ring-gray-500 outline-none focus:ring-gray-300 ${generatedByGpt ? 'bg-gray-800' : 'ring'}`}
        placeholder={
          !generatedByGpt &&
          (isWithGpt ?
            '자유롭게 적고 AI를 통해 다듬어 보세요.(선택사항)'
          : '여기에 적어주세요')
        }
        disabled={generatedByGpt}
      />
      <p className='caption-1 absolute right-5 bottom-5 text-gray-300'>{`${textLength}/400 byte`}</p>
      {canToggleAnonymous && (
        <button
          className='absolute bottom-5 left-5 flex items-center'
          onClick={toggleAnonymous}
        >
          <p className='body-1 mr-1.5 text-white'>익명</p>
          {isAnonymous ?
            <Icon name='checkBoxClick' />
          : <Icon name='checkBoxNone' />}
        </button>
      )}
    </div>
  );
}
