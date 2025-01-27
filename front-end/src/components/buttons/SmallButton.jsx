import classNames from 'classnames';
import Icon from '../Icon';

export default function SmallButton({ text, disabled, onClick, isOutlined = true }) {
  return (
    <button
      className={classNames(
        'flex justify-center items-center text-gray-300 rounded-300 px-4 py-2 ',
        isOutlined
          ? disabled
            ? ' border border-gray-500 text-gray-500'
            : 'cursor-pointer border border-lime-500 text-lime-500'
          : disabled
          ? ' bg-gray-800 text-gray-300 '
          : 'cursor-pointer bg-lime-500 text-gray-900',
      )}
      onClick={disabled ? () => {} : onClick}
      style={{
        width: '115px',
        height: '46px',
      }}
    >
      {text}
    </button>
  );
}
