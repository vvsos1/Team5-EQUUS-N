import { useRef, useState } from 'react';
import Icon from './Icon';

export default function Dropdown({
  triggerText = '',
  items,
  isSmall = false,
  isFromTime = true,
}) {
  const detailsRef = useRef(null);
  const [triggerTextState, setTriggerTextState] = useState(triggerText);

  if (isSmall) {
    return (
      <details ref={detailsRef} className='group'>
        <summary className='rounded-200 caption-2 flex size-fit cursor-pointer list-none items-center bg-gray-700 py-1 pr-1 pl-2.5 text-gray-200'>
          {triggerTextState}
          <Icon
            name='chevronDown'
            className='ml-0.5 transition group-open:rotate-180'
          />
        </summary>
        <ul className='rounded-200 absolute z-10 mt-2 flex size-fit flex-col gap-1 bg-gray-700 p-2'>
          {[...items, '전체 보기'].map((item, index) => (
            <li
              key={index}
              onClick={() => {
                detailsRef.current.open = false;
                setTriggerTextState(item);
              }}
              className={`body-1 cursor-pointer list-none px-2.5 py-1 ${item === triggerTextState ? 'text-gray-200' : 'text-gray-400'}`}
            >
              {item}
            </li>
          ))}
        </ul>
        {/* 배경 클릭을 위한 빽드롭필터 */}
        <div
          className='fixed inset-0'
          onClick={() => (detailsRef.current.open = false)}
        />
      </details>
    );
  } else {
    return (
      <details ref={detailsRef} className='group'>
        <summary className='rounded-200 caption-2 flex size-fit cursor-pointer list-none items-center bg-gray-700 py-1 pr-1 pl-2.5 whitespace-pre text-gray-200'>
          {triggerTextState} <span>{isFromTime ? ' 부터' : ' 까지'}</span>
          <Icon
            name='chevronDown'
            className='ml-0.5 transition group-open:rotate-180'
          />
        </summary>
        <ul className='rounded-200 absolute z-10 mt-2 flex size-fit max-h-56 flex-col gap-1 overflow-y-auto bg-gray-700 p-2'>
          {[
            //TODO: 시간대 배열
          ].map((item, index) => (
            <li
              key={index}
              onClick={() => {
                detailsRef.current.open = false;
                setTriggerTextState(item);
              }}
              className={`body-1 cursor-pointer list-none px-2.5 py-1 ${item === triggerTextState ? 'text-gray-200' : 'text-gray-400'}`}
            >
              {item}
            </li>
          ))}
        </ul>
        {/* 배경 클릭을 위한 빽드롭필터 */}
        <div
          className='fixed inset-0'
          onClick={() => (detailsRef.current.open = false)}
        />
      </details>
    );
  }
}
