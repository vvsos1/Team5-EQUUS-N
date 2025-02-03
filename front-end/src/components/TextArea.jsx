import { useReducer } from 'react';

export default function TextArea() {
  const [textLength, dispatch] = useReducer(
    (state, eventTarget) => eventTarget.value.length,
    0,
  );

  const onInput = (e) => {
    e.target.style.height = 'auto';
    e.target.style.height = `${e.target.scrollHeight}px`;
    dispatch(e.target);
  };
  return (
    <div className='relative'>
      <textarea
        onInput={onInput}
        className='text-gray-0 placeholder:body-1 rounded-300 relative w-full resize-none p-5 pb-10 ring ring-gray-500 outline-none focus:ring-gray-300'
        placeholder='여기에 적어주세요'
      />
      <p className='caption-1 absolute right-5 bottom-5 text-gray-300'>{`${textLength}/400 byte`}</p>
    </div>
  );
}
