/**
 * 토스트 컴포넌트
 * @param {object} props
 * @param {string} props.content - 토스트 내용
 * @returns {JSX.Element} - 토스트 컴포넌트
 */
export default function Toast({ content }) {
  return (
    <div
      className='text-gray-0 flex w-fit items-center justify-center rounded-full bg-gray-700 px-5 font-bold'
      style={{ height: '38px' }}
    >
      {content}
    </div>
  );
}
