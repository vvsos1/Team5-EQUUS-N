import { useReducer, useState } from 'react';
import Icon from './Icon';
import { transformToBytes } from '../utility/inputChecker';

export default function TextArea({
  isWithGpt = false,
  canToggleAnonymous = false,
  generatedByGpt = false,
  isAnonymous = false,
  toggleAnonymous,
  textLength,
  setTextLength,
  setTextContent,
}) {
  const [overflownIndex, setOverflownIndex] = useState();

  const onInput = (e) => {
    const { byteCount, overflowedIndex } = transformToBytes(e.target.value);
    setTextLength(byteCount);

    if (byteCount >= 399) {
      if (!overflownIndex) {
        setOverflownIndex(overflowedIndex);
      }
      if (overflownIndex)
        e.target.value = e.target.value.slice(0, overflownIndex);
    } else {
      if (overflownIndex) setOverflownIndex(null);
    }

    setTextContent(e.target.value);
  };

  return (
    <div
      className={`rounded-300 flex h-fit w-full flex-col border-white p-5 ring-gray-500 has-focus:ring-gray-300 ${generatedByGpt ? 'bg-gray-800' : 'ring'}`}
    >
      <textarea
        onInput={onInput}
        className={`text-gray-0 placeholder:body-1 scrollbar-hidden relative max-h-56 min-h-44 w-full resize-none outline-none`}
        placeholder={
          generatedByGpt ? undefined
          : isWithGpt ?
            '자유롭게 적고 AI를 통해 다듬어 보세요.(선택사항)'
          : '여기에 적어주세요'
        }
        disabled={generatedByGpt}
      />
      <div className='mt-2 flex w-full justify-between'>
        {canToggleAnonymous ?
          <button className='flex items-center' onClick={toggleAnonymous}>
            <p className='body-1 mr-1.5 text-white'>익명</p>
            {isAnonymous ?
              <Icon name='checkBoxClick' />
            : <Icon name='checkBoxNone' />}
          </button>
        : <div />}
        <p className='caption-1 text-gray-300'>{`${textLength}/400 byte`}</p>
      </div>
    </div>
  );
}
