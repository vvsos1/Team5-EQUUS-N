export default function Toast({ content }) {
  return (
    <div
      className='flex justify-center items-center w-fit bg-gray-700 text-gray-0 rounded-full px-5 font-bold'
      style={{ height: '38px' }}
    >
      {content}
    </div>
  );
}
