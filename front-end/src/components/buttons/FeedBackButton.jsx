export default function FeedBackButton({ currentFeedback, onClick }) {
  return (
    <div className='flex gap-3'>
      <div
        className={`rounded-400 flex cursor-pointer flex-col items-center justify-center gap-7 bg-gray-800 px-6.5 py-9 outline-lime-500 select-none ${
          currentFeedback === 'good' ? 'outline-2' : ''
        }`}
        style={{ width: '170px' }}
        onClick={() => {
          onClick('good');
        }}
      >
        <p className='text-4xl'>😀</p>
        <div className='flex flex-col items-center gap-3'>
          <p className='caption-1 text-center text-gray-300'>
            개인의 강점과
            <br />
            행동을 칭찬해주는
          </p>
          <p className='header-4 text-lime-500'>칭찬해요</p>
        </div>
      </div>
      <div
        className={`rounded-400 flex cursor-pointer flex-col items-center justify-center gap-7 bg-gray-800 px-6.5 py-9 outline-lime-500 select-none ${
          currentFeedback === 'bad' ? 'outline-2' : ''
        }`}
        style={{ width: '170px' }}
        onClick={() => {
          onClick('bad');
        }}
      >
        <p className='text-4xl'>🤔</p>
        <div className='flex flex-col items-center gap-3'>
          <p className='caption-1 text-center text-gray-300'>
            존중하는 말투로
            <br />
            개선 방법을 제공하는
          </p>
          <p className='header-4 text-lime-500'>아쉬워요</p>
        </div>
      </div>
    </div>
  );
}
