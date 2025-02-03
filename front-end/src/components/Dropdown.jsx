export default function Dropdown({ triggerText, items }) {
  return (
    <details>
      <summary className='rounded-200 cursor-pointer list-none bg-gray-700 px-5 py-7'>
        {triggerText}
      </summary>
      <p>Here's some content</p>
    </details>
  );
}
